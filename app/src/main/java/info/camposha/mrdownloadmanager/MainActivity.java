package info.camposha.mrdownloadmanager;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private DownloadManager downloadManager;
    private String fileName=null;
    private long downLoadId;


    /**
     * Initialize download manager
     */
    private void initializeDownloadManager() {
        downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        fileName="Voyager";
    }

    /**
     * Set the title and desc of this download, to be displayed in notifications.
     */
    private void downloadFile(){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("https://raw.githubusercontent.com/Oclemy/SampleJSON/master/spacecrafts/voyager.jpg"));
        request.setTitle("Voyager")
                .setDescription("File is downloading...")
                .setDestinationInExternalFilesDir(this,
                        Environment.DIRECTORY_DOWNLOADS,fileName)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Enqueue the download.The download will start automatically once the download manager is ready
        // to execute it and connectivity is available.
        downLoadId=downloadManager.enqueue(request);
    }

    /**
     * Cancel downloads and remove them from the download manager.
     * If there is a downloaded file, partial or complete, it is deleted.
     */
    private void deleteDownloadedFile(){
        downloadManager.remove(downLoadId);
    }
    private void openOurDownload(){
        try {
            downloadManager.openDownloadedFile(downLoadId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "The File is Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * View all downloads in the downloadmanager
     */
    private void viewAllDownloads(){
        Intent intent=new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }

    /**
     * Handle button clicks
     * @param view
     */
    public void clickView(View view){
        switch (view.getId()){
            case R.id.downloadBtn:
                downloadFile();
                break;
            case R.id.openDownloadBtn:
                openOurDownload();
                break;
            case R.id.viewDownloadsBtn:
                viewAllDownloads();
                break;
            case R.id.deleteBtn:
                deleteDownloadedFile();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDownloadManager();
    }
}
