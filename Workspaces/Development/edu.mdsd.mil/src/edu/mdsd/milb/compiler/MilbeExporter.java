package edu.mdsd.milb.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.Output;

public class MilbeExporter {
	public boolean writeToFile(Milbe milbe, String path, Output out) {
		
		File file = new File(path);
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				out.err("[MilbeExporter.writeToFile] Could not create file " + path);
				e.printStackTrace();
			}
		}
		
		try (FileOutputStream fos = new FileOutputStream(path)) {
			out.println("[MilbeExporter.writeToFile] Exporting to " + path);
			fos.write(milbe.getBytesPrimitive());
			return true;
		} catch (FileNotFoundException e) {
			out.err("[MilbeExporter.writeToFile] file not found");
			e.printStackTrace();
		} catch (IOException e) {
			out.err("[MilbeExporter.writeToFile] IOException on write");
			e.printStackTrace();
		}
		
		return false;
	}
}
