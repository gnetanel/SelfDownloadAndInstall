# SelfDownloadAndInstall
Demonstrating how Anroid app download and install/update applications

Key point to notice (see in project code for details)
1) Add the file provider in the Manifest file
<provider
            android:name="androidx.core.content.FileProvider"
            android:grantUriPermissions="true"
            android:exported="false"
            android:authorities="com.netanel.downloadandinstall"> (this shall be your app package name...)

            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_provider_paths"/> (see below for details on this)
</provider>

2) Add the xml file reference from the provider above (@xml/file_provider_paths)
In my example <files-path  name="files" path="/" /> this refer to internal directory that file is located on)


3) Set the intent correctly (see below)

            Intent installIntent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, outputFile);
            installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(installIntent);

4) update in MainActivity, appUrl to refer to URL you apk is located on 

