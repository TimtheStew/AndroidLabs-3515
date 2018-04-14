package edu.temple.lab08simplebrowser;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebTabFragment extends android.support.v4.app.Fragment {

    //a name to call our argument by
    private static final String URL = "url";

    private static final String POS = "position";
    //the fragments current URL
    private String currentUrl;

    //the WebView
    private WebView webView;

    //the interface listener
    OnUrlChangedListener mListener;

    //our index
    int mPosition;

    public WebTabFragment() {
        // Required empty public constructor
    }

    // the factory for the WebTabFragment,
    public static WebTabFragment newInstance(String url, int position) {
        WebTabFragment webTabFragment = new WebTabFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putInt(POS, position);
        webTabFragment.setArguments(args);
        return webTabFragment;
    }

    //Overriding onAttach to make sure the Activity we've attached to implements the interface we
    //specified below
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mListener = (OnUrlChangedListener) context;
        }catch(ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Must Implement OnUrlChangedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //load from savedInstanceState
        if (savedInstanceState != null) {
            currentUrl = savedInstanceState.getString(URL);
        } else if (getArguments() != null){
            //or from Arguments, if set by factory
            currentUrl = getArguments().getString(URL);
            mPosition = getArguments().getInt(POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_tab, container, false);
        // Grab a reference to the WebView
        webView = view.findViewById(R.id.webview);
        // Set a new WebView Client
        webView.setWebViewClient(new WebViewClient(){
            // almost word for word what he showed us in class
            // overriding the onPageFinished callback, so that every time a page finishes loading,
            // we can grab a copy of what URL was loaded.
            @Override
            public void onPageFinished(WebView view, String url){
                //calling the super
                super.onPageFinished(view, url);
                //setting our local value
                currentUrl = url;
                //calling a method from the interface we defined to update the EditText
                mListener.onUrlChanged(url, mPosition);
            }
        });
        //enabling JavaScript in the WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(currentUrl);
        return view;
    }

    //to save our current URL if garbage collected or some such
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(URL, currentUrl);
        super.onSaveInstanceState(savedInstanceState);
    }
    public interface OnUrlChangedListener {
        public void onUrlChanged(String url, int position);
    }

    // a public method to navigate to the current URL
    public void navigateToCurrentUrl(){
        webView.loadUrl(currentUrl);
        mListener.onUrlChanged(currentUrl, mPosition);
    }

    //a public method to navigate to a specific URL
    public void navigateTo(String url) {
        webView.loadUrl(url);
        currentUrl = url;
        mListener.onUrlChanged(url, mPosition);
    }
}
