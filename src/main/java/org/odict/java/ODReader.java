package org.odict.java;

import org.odict.java.schema.Dictionary;
import org.xerial.snappy.Snappy;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ODReader {
    public static ODictionary read(BufferedInputStream stream) throws IOException {
        // Read in signature and validate it
        byte[] signature = new byte[5];
        stream.read(signature, 0, 5);

        // Validate file signature
        if (!new String(signature).equals("ODICT")) {
            throw new Error("Invalid ODict file signature");
        }

        // Read in version number
        byte[] version_b = new byte[2];
        stream.read(version_b);
        short version = ByteBuffer.wrap(version_b).order(ByteOrder.LITTLE_ENDIAN).getShort();

        // Read in length of compressed data
        byte[] compressed_size_b = new byte[8];
        stream.read(compressed_size_b);
        long compressed_size = ByteBuffer.wrap(compressed_size_b).order(ByteOrder.LITTLE_ENDIAN).getLong();

        // Read in compressed data
        byte[] compressed = new byte[(int) compressed_size];
        stream.read(compressed);
        stream.close();

        // Decompress data
        byte[] uncompressed = Snappy.uncompress(compressed);

        // Convert to dictionary and return
        Dictionary dict = Dictionary.getRootAsDictionary(ByteBuffer.wrap(uncompressed));

        return (dict != null) ? new ODictionary(version, dict) : null;
    }

    public static ODictionary read(String filepath) throws IOException {
        return read(new BufferedInputStream(new FileInputStream(filepath)));
    }
}
