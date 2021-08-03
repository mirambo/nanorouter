package com.benjamen.newapi;

import android.app.ProgressDialog;
import android.arch.core.executor.TaskExecutor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SmsReceiver extends BroadcastReceiver {

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_create_product = "http://nano.codeafrica.co.tz/api/insert.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    List<NameValuePair> params = new ArrayList<NameValuePair>();

    @Override
    public void onReceive(final Context context, Intent intent) {

        Bundle myBudle = intent.getExtras();
        SmsMessage[] messages = null;
        String strmessageHeader = "";
        String strmessageBody = "";

        String phoneNo_ = "";

        try {
            if (myBudle != null) {
                Object[] pdus = (Object[]) myBudle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    strmessageHeader = messages[i].getOriginatingAddress();
                    strmessageBody += messages[i].getMessageBody();
                    messages[i].getMessageBody();

                }

               final String phoneNo = strmessageHeader;
                phoneNo_ = strmessageHeader;


                String currentString = strmessageBody;

                //Check the message here

                String[] separated = currentString.split(":");

                final String DATE = separated[1];
                String uknown = separated[2];
                final String CENTER = separated[3];
                String uknown1 = separated[4];
                final String MEr = separated[5];
                String uknown2 = separated[6];
                final String KEr = separated[7];
                String uknown3 = separated[8];
                final String HALFr = separated[9];
                String uknown4 = separated[10];
                final String ONEFIVEr = separated[11];
                String uknown5 = separated[12];
                final String FIVEr = separated[13];
                String uknown6 = separated[14];
                final String TENr = separated[15];
                String uknown7 = separated[16];
                final String TWENTr = separated[17];
                String uknown8 = separated[18];
                final String MEb = separated[19];
                String uknown9 = separated[20];
                final String KEb = separated[21];
                String uknown10 = separated[22];
                final String HALFb = separated[23];
                String uknown11 = separated[24];
                final String ONEFIVEb = separated[25];
                String uknown12 = separated[26];
                final String FIVEb = separated[27];
                String uknown13 = separated[28];
                final String TENb = separated[29];
                String uknown14 = separated[30];
                final String TWENThb = separated[31];
                String uknown15 = separated[32];
                final String TWENTsb = separated[33];


                if(currentString.startsWith("NANO")){

                    class CreateNewProduct extends AsyncTask<String, String, String> {

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                            SmsManager smsManager1 = SmsManager.getDefault();
                            smsManager1.sendTextMessage(phoneNo, null, "Report Yako Imepokelewa. Asante", null, null);

                        }

                        protected String doInBackground(String... args) {

                            // Building Parameters
                            params.add(new BasicNameValuePair("Date",DATE ));
                            params.add(new BasicNameValuePair("Centerno",CENTER ));
                            params.add(new BasicNameValuePair("Mer",MEr));
                            params.add(new BasicNameValuePair("Ker",KEr));
                            params.add(new BasicNameValuePair("R05r",HALFr ));
                            params.add(new BasicNameValuePair("R15r", ONEFIVEr));
                            params.add(new BasicNameValuePair("R5r", FIVEr));
                            params.add(new BasicNameValuePair("R10r",TENr ));
                            params.add(new BasicNameValuePair("R20r", TWENTr));
                            params.add(new BasicNameValuePair("Meb",MEb));
                            params.add(new BasicNameValuePair("Keb",KEb));
                            params.add(new BasicNameValuePair("B05b",HALFb ));
                            params.add(new BasicNameValuePair("B15b", ONEFIVEb));
                            params.add(new BasicNameValuePair("B5b", FIVEb));
                            params.add(new BasicNameValuePair("B10b",TENb ));
                            params.add(new BasicNameValuePair("B20hb", TWENThb));
                            params.add(new BasicNameValuePair("B20sb", TWENTsb));

                            // getting JSON Object
                            // Note that create product url accepts POST method
                            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                                    "POST", params);

                            // check log cat fro response
                            Log.d("Create Response", json.toString());

                            // check for success tag
                            try {
                                int success = json.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                    // successfully created product

                                } else {
                                    // failed to create product
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            return null;
                        }

                        protected void onPostExecute(String file_url) {
                            Toast.makeText(context, "Final Process", Toast.LENGTH_SHORT).show();

                        }
                    }
                new CreateNewProduct().execute();

                }else {
                        //Send back sms feedback to user when sms failed to be processed to db
                    SmsManager smsManager1 = SmsManager.getDefault();
                    smsManager1.sendTextMessage(phoneNo, null, "Rudia tena kutuma report kwa Usahihi", null, null);

                }
            }
        }catch (Exception e) {
            e.printStackTrace();

            SmsManager smsManager1 = SmsManager.getDefault();
            smsManager1.sendTextMessage(phoneNo_, null, "Umekosea mpangilio", null, null);

        }
    }
}


