package com.nettys.privateporto.coder;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * 创建Jboss Marshalling解码器MarshallingDecoder
 *
 * @return
 */
public class MarshallingCodeCFactory {

    public static MarshallingDecoder buildMarshallingDecoder() {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(
                marshallerFactory, configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    /**
     * 创建Jboss Marshalling编码器MarshallingEncoder
     *
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(
                marshallerFactory, configuration);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }

    /**
     * 转协议
     *
     * @return
     */
    public static Marshaller buildMarshalling() {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Marshaller marshaller = null;
        try {
            marshaller = marshallerFactory
                    .createMarshaller(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marshaller;
    }

    /**
     * 转对象
     *
     * @return
     */
    public static Unmarshaller buildUnmarshaller() {
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = marshallerFactory
                    .createUnmarshaller(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return unmarshaller;
    }

}
