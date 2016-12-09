package com.zy.rxjavaretrofitokhttp.http.stringconverter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by G on 2016/12/9.
 */

/**
 * 自定义string类型转换器，后台返回string类型数据解析
 */
public class StringConverterFactory extends Converter.Factory {

    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    public StringConverterFactory() {
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestBodyConverter();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringResponseBodyConverter();
    }


    //Response响应体类
    private class StringResponseBodyConverter implements Converter<ResponseBody, String> {

        @Override
        public String convert(ResponseBody value) throws IOException {
            try {
                return value.string();
            } finally {
                value.close();
            }
        }
    }

    //Request 请求体类
    private class StringRequestBodyConverter implements Converter<String, RequestBody> {

        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        public StringRequestBodyConverter() {
        }

        @Override
        public RequestBody convert(String value) throws IOException {

            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            writer.write(value);
            writer.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }
}
