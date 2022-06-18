package com.himma.my_rpc_framework.common;

import java.io.*;

public class ObjectSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] bytes = null;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Object obj = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();

            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }

    //0 表示原生序列化器
    @Override
    public int getType() {
        return 0;
    }
}
