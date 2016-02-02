package sidespell.tech.midtermexam.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sidespell.tech.midtermexam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewListFragment extends Fragment {

    private ArrayAdapter<String> mAlbumAdapter;

    public static RecyclerViewListFragment newInstance() {
        return new RecyclerViewListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recycler_view_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Find all the views
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerListView);

        TextView tvEmpty = (TextView) view.findViewById(android.R.id.empty);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Unlike a ListView, a RecyclerView needs a LayoutManager to manage the positioning of its
        // items. You could define your own LayoutManager by extending the RecyclerView.LayoutManager
        // class. However, in most cases, you could simply use one of the predefined LayoutManager
        // subclasses. In our case, since we are to create a ListView, we will be using the
        // LinearLayoutManager.
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        // Create a new instance of the adapter
        //    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(),
        //         R.layout.recycler_list_item, mController.getMovies());

        // Set the Adapter
//        recyclerView.setAdapter(adapter);

        //      if (adapter.getItemCount() == 0) {
        //        tvEmpty.setVisibility(View.VISIBLE);
        //  } else {
        //    tvEmpty.setVisibility(View.GONE);
        //}

}
    public class FetchAlbumTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchAlbumTask.class.getSimpleName();

        private String[] getAlbumDataFromJson(String albumJsonStr, int limit) throws JSONException {
            final String ALBUM_LIST = "list";
            final String ALBUM_NAME = "album";
            final String ALBUM_ARTIST = "artist";

            JSONObject albumJson = new JSONObject(albumJsonStr);
            JSONArray albumArray = albumJson.getJSONArray(ALBUM_LIST);

            String[] resultStrs = new String[limit];

            for (int i = 0; i < albumArray.length(); i++) {
                String albumName;
                String albumArtist;

                JSONObject albumData = albumArray.getJSONObject(i);

                JSONObject albumObject = albumData.getJSONArray(ALBUM_NAME).getJSONObject(0);
                albumName = albumObject.getString(ALBUM_NAME);

                JSONObject artistObject = albumData.getJSONObject(ALBUM_ARTIST);
                albumArtist = artistObject.getString(ALBUM_ARTIST);

                resultStrs[i] = albumName + " - " + albumArtist;


            }
            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return resultStrs;
        }



        @Override
        protected String doInBackground(int... params) {
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String albumJsonStr = null;

            String method = "album.getinfo";
            int limit = 50;

            String appId = "dece00f28d8ec006e9225a29528a5101";

            try {
                final String API_BASE_URL = "http://ws.audioscrobbler.com/2.0/?";
                final String METHOD_PARAM = "method";
                final String ARTIST_PARAM = "artist";
                final String ALBUM_PARAM = "album";

                final String API_KEY_PARAM = "api_key";

                Uri.Builder builtUri = Uri.parse(API_BASE_URL).buildUpon()
                        .appendQueryParameter(METHOD_PARAM, method)
                        .appendQueryParameter(ARTIST_PARAM, params[0])
                        .appendQueryParameter(ALBUM_PARAM, params[0])
                        .appendQueryParameter(API_KEY_PARAM, appId);

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                albumJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getAlbumDataFromJson(albumJsonStr, limit);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }





    }