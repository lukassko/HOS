package com.app.hos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.hibernate.type.SerializationException;

public class HosSerialization {

    public static Object deserialize(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        ObjectInputStream in = null;
        try {
            // stream closed in the finally
            in = new ObjectInputStream(inputStream);
            return in.readObject();
            
        } catch (ClassNotFoundException ex) {
            throw new SerializationException("Exception during deserialization",ex);
        } catch (IOException ex) {
            throw new SerializationException("Exception during deserialization", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }
    }

}
