package com.example.product.filter;


import com.example.product.dto.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

@Component
@Slf4j
//@Order(1)
public class RequestResponseLoggers implements Filter {
    @Autowired
    private ObjectMapper objectMapper;

    //passowrd,SSN,adhar, pan personal
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        MyCustomHttpRequestWrapper requestWrapper= new MyCustomHttpRequestWrapper((HttpServletRequest) request);
        String uri=requestWrapper.getRequestURI();

       log.info("request URI :{}",requestWrapper.getRequestURI());
       log.info("request Method : {}",requestWrapper.getMethod());
        String requestdata = new String(requestWrapper.getByteArray());
       if("/v1/addproduct".equalsIgnoreCase(uri)) {

          Product product=objectMapper.readValue(requestdata,Product.class);
          product.setCurrency("*****");
           requestdata=objectMapper.writeValueAsString(product);
       }
       log.info("requestBody: \n {}", requestdata);
        MyCustomHttpResponseWrapper responseWrapper = new MyCustomHttpResponseWrapper((HttpServletResponse)response);
        filterChain.doFilter(requestWrapper,responseWrapper);

        log.info("response status: {}", responseWrapper.getStatus());

        String responsedata=new String(responseWrapper.getBaos().toByteArray());
        if("/v1/addproduct".equalsIgnoreCase(uri)) {
            Product product = objectMapper.readValue(responsedata, Product.class);
            product.setCurrency("*****");
            responsedata = objectMapper.writeValueAsString(product);
        }
        log.info("responseBody: {}",responsedata);
    }
    private class MyCustomHttpRequestWrapper extends HttpServletRequestWrapper
    {
       private byte[] byteArray;

        public byte[] getByteArray() {
            return byteArray;
        }

        public MyCustomHttpRequestWrapper(HttpServletRequest request) {
            super(request);
            try {
                byteArray=IOUtils.toByteArray(request.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Issue while reading request stream");
            }
        }
        public ServletInputStream getInputStream()
        {
            return new MyDelegatingServletInputStream(new ByteArrayInputStream(byteArray));
        }
    }

    private class MyCustomHttpResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayOutputStream baos=new ByteArrayOutputStream();
       private  PrintStream printStream=new PrintStream(baos);

        public ByteArrayOutputStream getBaos() {
            return baos;
        }



        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new MyDelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(),printStream));
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter((new TeeOutputStream(super.getOutputStream(),printStream)));
        }

        public MyCustomHttpResponseWrapper(HttpServletResponse response) {
            super(response);

        }
    }
}
