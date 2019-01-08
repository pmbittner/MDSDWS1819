package edu.mdsd.milb.compiler;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mdsd.mil.JumpMarker;

public class Milbe {
	private static class JumpLabel {
		static int Unknown_Address = 0;
		
		int address;
		private boolean known = false;
		
		List<Integer> references;
		
		private JumpLabel(int address, boolean known) {
			this.address = address;
			this.known = known;
			
			if (isUnknown())
				references = new ArrayList<>();
		}

		void addReference(int currentAddress) {
			references.add(currentAddress);
		}
		
		void know() {
			known = true;
		}
		
		boolean isUnknown() {
			return !known;
		}
		
		static JumpLabel createKnown(int address) {
			return new JumpLabel(address, true);
		}
		
		static JumpLabel createUnknown() {
			return new JumpLabel(Unknown_Address, false);
		}
	}
	
	private static byte[] toBytes(int arg) {
		return ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(arg).array();
	}
	
	private ArrayList<Byte> bytes;
	private Map<String, JumpLabel> jumpTargets;
	
	public Milbe() {
		bytes = new ArrayList<>();
		jumpTargets = new HashMap<>();
	}
	
	public void pushInstruction(ByteCode bytecode) {
		bytes.add((byte) bytecode.ordinal());
	}
	
	private void writeAt(int index, int value) {
		byte[] intAsByte = toBytes(value);

		for (byte b : intAsByte) {
		   bytes.set(index, b);
		   ++index;
		}
	}
	
	public void pushArgument(byte[] byteArray) {
		for (byte b : byteArray) {
			bytes.add(b);
		}
	}
	
	public void pushArgument(int arg) {
		pushArgument(toBytes(arg));
	}
	
	public void pushArgument(String arg) {
		pushArgument(arg.getBytes(StandardCharsets.UTF_8));
	}
	
	public void pushArgument(JumpMarker marker) {
		String key = marker.getName();
		JumpLabel label = jumpTargets.get(key);
		
		if (label == null) {
			label = JumpLabel.createUnknown();
			jumpTargets.put(key, label);
		}
		
		if (label.isUnknown()) {
			int currentAddress = end();
			pushArgument(JumpLabel.Unknown_Address);
			label.addReference(currentAddress);		
		} else {
			pushArgument(label.address);
		}
	}
	
	public void registerJumpMarker(JumpMarker marker) {
		String key = marker.getName();
		int address = end();
		JumpLabel label = jumpTargets.get(key);
		
		if (label == null) {
			jumpTargets.put(key, JumpLabel.createKnown(address));
		} else if(label.isUnknown()) {
			// now we know this previously unknown label
			label.address = address;
			
			for (Integer reference : label.references) {
				writeAt(reference, address);
			}
			
			label.know();
		}
	}
	
	public int back() {
		return end() - 1;
	}
	
	public int end() {
		return bytes.size();
	}
	
	public ArrayList<Byte> getBytesList() {
		return bytes;
	}
	
	public Byte[] getBytes() {
		Byte[] Bytes = new Byte[bytes.size()];
		return bytes.toArray(Bytes);
	}
	
	public byte[] getBytesPrimitive() {
		Byte[] BytesArray = getBytes();
		byte[] bytesArray = new byte[BytesArray.length];
		
		for (int i = 0; i < bytesArray.length; ++i) {
			bytesArray[i] = BytesArray[i];
		}
		
		return bytesArray;
	}
}
