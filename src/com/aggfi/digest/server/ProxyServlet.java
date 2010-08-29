package com.aggfi.digest.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;



@SuppressWarnings("serial")
public class ProxyServlet extends HttpServlet {
	Logger LOG  = Logger.getLogger(ProxyServlet.class.getName());
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		
		
		try {
            URL url = new URL("http://localhost:8889/admin/jsonrpc" + "?cachebust=" + new Date().getTime());
//			 URL url = new URL("http://digestbotty.appspot.com/admin/jsonrpc" + "?cachebust=" + new Date().getTime());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(65000);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            InputStream is = req.getInputStream();
            byte[] inputStreamByteArr = new byte[is.available()];
            is.read(inputStreamByteArr);
        	for(int i = 0; i< inputStreamByteArr.length; i++){
        		wr.write(inputStreamByteArr[i]);
        	}
            
            wr.flush(); 
//            wr.close();
            
            try{
            	 if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                	 writeRespOnSuccess(resp, sb, connection);
                } else {
                	LOG.log(Level.SEVERE, "Response code: " + connection.getResponseCode());
                	
                }
            }catch(IOException e){
            	
            	LOG.log(Level.SEVERE, "Response code: " + connection.getResponseCode(), e);
            }
           
        } catch (MalformedURLException e) {
        	LOG.log(Level.SEVERE, "", e);
        } catch (IOException e) {
        	LOG.log(Level.SEVERE, "", e);
        }
		
	}
	private void writeRespOnSuccess(HttpServletResponse resp, StringBuilder sb,
			HttpURLConnection connection)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		OutputStreamWriter wrRes = new OutputStreamWriter(resp.getOutputStream()); 
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			wrRes.write(line);
		}
		reader.close();
		wrRes.flush();
	}

}
