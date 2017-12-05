package com.lrdwhyt.notepad.dbtasks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.DatabaseSubscriber;
import com.lrdwhyt.notepad.NoteDBContract;
import com.lrdwhyt.notepad.NoteEntry;
import com.lrdwhyt.notepad.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ReadAllTags extends AsyncTask<Void, Void, Void> {

    private DatabaseManager dbh;
    private Cursor selectedNoteEntries;
    private DatabaseSubscriber dbs;

    public ReadAllTags(DatabaseManager dbh, DatabaseSubscriber dbs) {
        this.dbh = dbh;
        this.dbs = dbs;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = new SQLiteHelper(dbh.getContext()).getReadableDatabase();
        selectedNoteEntries = db.query(NoteDBContract.Tags.TABLE_NAME, new String[]{NoteDBContract.Tags._ID, NoteDBContract.Tags.COLUMN_NAME}, null, null, null, null, "NAME desc");
        return null;
    }

    @Override
    protected void onPostExecute(Void _void) {
        // Do something with selectedNoteEntries
        List<String> results = new ArrayList<>();
        while (selectedNoteEntries.moveToNext()) {
            results.add(selectedNoteEntries.getString(1));
        }
        selectedNoteEntries.close();
        dbs.onReadMultipleTags(results);
        super.onPostExecute(_void);
    }

}