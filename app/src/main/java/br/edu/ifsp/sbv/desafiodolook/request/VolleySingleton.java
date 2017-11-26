package br.edu.ifsp.sbv.desafiodolook.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.util.Log;

/**
 * Created by Adriel on 11/24/2017.
 */

public class VolleySingleton {
    private static VolleySingleton singletonInstance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(30);
            //30 -> the maximum number of entries in the cache.

            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
                Log.d("CachedItems", String.valueOf(lruCache.size()));
            }

            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new VolleySingleton(context);
        }
        return singletonInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.requestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}