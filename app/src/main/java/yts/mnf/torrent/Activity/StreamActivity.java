package yts.mnf.torrent.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import yts.mnf.torrent.R;

public class StreamActivity extends AppCompatActivity {

/*
    private static final String TORRENT = "Torrent";
    private Button button;
    private ProgressBar progressBar;
    private TorrentStream torrentStream;

    private String streamUrl = "magnet:?xt=urn:btih:749E77BBFEBD97E689C132E3B663BB89425476DC&amp;tr=udp://glotorrents.pw:6969/announce&amp;tr=udp://tracker.opentrackr.org:1337/announce&amp;tr=udp://torrent.gresille.org:80/announce&amp;tr=udp://tracker.openbittorrent.com:80&amp;tr=udp://tracker.coppersurfer.tk:6969&amp;tr=udp://tracker.leechers-paradise.org:6969&amp;tr=udp://p4p.arenabg.ch:1337&amp;tr=udp://tracker.internetwarriors.net:1337";

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar.setProgress(0);
            if(torrentStream.isStreaming()) {
                torrentStream.stopStream();
                button.setText("Start stream");
                return;
            }
            torrentStream.startStream(streamUrl);
            button.setText("Stop stream");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);


        String action = getIntent().getAction();
        Uri data = getIntent().getData();
        if (action != null && action.equals(Intent.ACTION_VIEW) && data != null) {
            try {
                streamUrl = URLDecoder.decode(data.toString(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        TorrentOptions torrentOptions = new TorrentOptions.Builder()
                .saveLocation(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                .removeFilesAfterStop(true)
                .build();

        torrentStream = TorrentStream.init(torrentOptions);
        torrentStream.addListener(this);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        progressBar.setMax(100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onStreamPrepared(Torrent torrent) {
        Log.e("TORRENT", "OnStreamPrepared");
        torrent.startDownload();
    }

    @Override
    public void onStreamStarted(Torrent torrent) {
        Log.e("TORRENT", "onStreamStarted");

    }

    @Override
    public void onStreamError(Torrent torrent, Exception e) {
        Log.e("TORRENT", "onStreamError");

        button.setText("Start stream");

    }

    @Override
    public void onStreamReady(Torrent torrent) {
        Log.e("TORRENT", "onStreamReady");

        progressBar.setProgress(100);
        Log.d(TORRENT, "onStreamReady: " + torrent.getVideoFile());

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(torrent.getVideoFile().toString()));
        intent.setDataAndType(Uri.parse(torrent.getVideoFile().toString()), "video/mp4");
        startActivity(intent);
    }

    @Override
    public void onStreamProgress(Torrent torrent, StreamStatus status) {
        Log.e("TORRENT", "onStreamProgress");

        if(status.bufferProgress <= 100 && progressBar.getProgress() < 100 && progressBar.getProgress() != status.bufferProgress) {
            Log.d(TORRENT, "Progress: " + status.bufferProgress);
            progressBar.setProgress(status.bufferProgress);
        }
    }

    @Override
    public void onStreamStopped() {
        Log.e("TORRENT", "onStreamStopped");

    }
*/}
