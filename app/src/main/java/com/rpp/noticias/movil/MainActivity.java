package com.rpp.noticias.movil;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity{


    String urlCompartir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        WebView rppWeb = (WebView)findViewById(R.id.rppWeb);
        rppWeb.setWebViewClient(new WebViewClient());

        rppWeb.getSettings().setAppCacheEnabled(true);
        rppWeb.getSettings().setJavaScriptEnabled(true);

        rppWeb.loadUrl("https://rpp.pe");

        rppWeb.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.i("RPP","Should" + " " + url);

                if (!url.contains("facebook") && !url.contains("twitter"))
                {
                    urlCompartir = url;
                    Log.i("RPP","la url para compartir es" + " " + urlCompartir);
                }

                if (url.contains("facebook"))
                {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    //String urlFb = urlCompartir.replaceAll("https","http");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, urlCompartir);
                    startActivity(Intent.createChooser(sharingIntent, "Selecciona Facebook para compartir"));
                    return true;
                }

                if (url.contains("twitter"))
                {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(sharingIntent, "Selecciona Twitter para compartir"));
                    return true;
                }

                if (url.contains("whatsapp"))
                {
                    Intent intentWsp = new Intent(Intent.ACTION_VIEW);
                    intentWsp.setData(Uri.parse(url));
                    startActivity(intentWsp);
                    return true;
                }

                if (url.contains("messenger"))
                {
                    Intent intentMsn = new Intent(Intent.ACTION_VIEW);
                    intentMsn .setData(Uri.parse(url));
                    startActivity(intentMsn);
                    return true;
                }

                if (!url.contains("https://rpp.pe"))
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("RPP","Pag Web cargada" + " " + url);
            }
        });

    }

    @Override
    public void onBackPressed() {
        WebView rppWeb = (WebView)findViewById(R.id.rppWeb);

        if (rppWeb.canGoBack())
        {
            rppWeb.goBack();
        }else
        {
            super.onBackPressed();
        }
    }

}
