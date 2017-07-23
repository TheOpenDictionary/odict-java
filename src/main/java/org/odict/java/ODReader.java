package org.odict.java;

import org.odict.java.schema.Dictionary;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ODReader {
    static public ODictionary read(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        ByteBuffer buffer = null;
        byte[] bytes = Files.readAllBytes(path);

        // Read in signature and validate it
        byte[] signature = new byte[5];
        System.arraycopy(bytes, 0, signature, 0, signature.length);
        assert new String(signature).equals("ODICT");

        byte[] version_b = new byte[2];
        System.arraycopy(bytes, 5, version_b, 0, version_b.length);
        short version = ByteBuffer.wrap(version_b).order(ByteOrder.LITTLE_ENDIAN).getShort();

        byte[] compressed_size_b = new byte[8];
        System.arraycopy(bytes, 7, compressed_size_b, 0, compressed_size_b.length);
        long compressed_size = ByteBuffer.wrap(compressed_size_b).order(ByteOrder.LITTLE_ENDIAN).getLong();

        int header_size = signature.length + version_b.length + compressed_size_b.length;
        assert (header_size + compressed_size == bytes.length);

        byte[] compressed = Arrays.copyOfRange(bytes, header_size, (int) (header_size + compressed_size));
        byte[] uncompressed = Snappy.uncompress(compressed);

        Dictionary dict = Dictionary.getRootAsDictionary(ByteBuffer.wrap(uncompressed));

        if (dict != null) return new ODictionary(version, dict);
        else return null;
    }
}
