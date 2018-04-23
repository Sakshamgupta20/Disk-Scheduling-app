package labs.a.s.disk_scheduling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.abs;

public class Main2Activity extends AppCompatActivity {

    EditText Etrack;
    int header=0,pre_request=0,ncylinder=0;
    int ntrack=0,he;
    Integer[] track_request = new Integer[100];
    Integer[] track_original = new Integer[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ncylinder = getIntent().getIntExtra("cylinder",0);
        header = getIntent().getIntExtra("header",0);
        pre_request = getIntent().getIntExtra("prerequest",0);

        Etrack = (EditText)findViewById(R.id.trackrequest_edit);


    }

    public void addtrack(View view) {
        //check if empty
        if (TextUtils.isEmpty(Etrack.getText()) )
        {
            Toast.makeText(Main2Activity.this, "Please Enter the track value", Toast.LENGTH_SHORT).show();
        }
        else{
            //Getting data from edittext
            int track = Integer.parseInt(Etrack.getText().toString());
            Etrack.setText("");
            track_original[ntrack]=track;
            track_request[ntrack]=track;
            ntrack++;
           fcfs();
          sstf();
         sort();
         scan();
            look();
          c_scan();
           clook();
        }
    }

    void sort() {
        int temp=0;
        for (int i = 0; i < ntrack; i++) {
            for (int j = i; j < ntrack; j++) {
                if (track_request[i] > track_request[j]) {
                     temp = track_request[i];
                    track_request[i] = track_request[j];
                    track_request[j] = temp;
                }
            }
        }
        for (int i = 0; i < ntrack-1; i++) {
            if (header >= track_request[i]&& header < track_request[i + 1]) {
                he = i;
            }
        }

        }



    void fcfs()
    {
        int sum = 0;
        Integer sequence[] = new Integer[100];
        Integer arr[] = new Integer[100];
        int head = header,i;
        for(i=0;i<ntrack;i++)
        {
            arr[i]=track_original[i];
        }
        for(i=0;i<ntrack;i++)
        {

            sequence[i]=head-track_original[i];
            if(sequence[i]<0)
            {
                sequence[i]=track_original[i]-head;
            }
            head=track_original[i];
            sum=sum+sequence[i];
        }
        TextView textView=(TextView)findViewById(R.id.fcfssum);
        String temp="Seek Time="+String.valueOf(sum);
        textView.setText(temp);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fcfs);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        FCFSAdapter adapter = new FCFSAdapter(this);

        adapter.setBakingData(arr);
        adapter.setBakingNumber(ntrack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    void sstf()
    {
        int sum=0;
        int cheader=header;
        Integer sequence[] = new Integer[100];
        Boolean inc[]=new Boolean[100];
        for(int i=0;i<ntrack;i++) {
            inc[i] = false;
        }

        for(int i=0;i<ntrack;i++)
        {
            int min=99999;
            int index=0;
            for(int j=0;j<ntrack;j++)
            {
                if(abs(cheader-track_original[j])<min && inc[j]==false)
                {
                    min=abs(cheader-track_original[j]);
                    index=j;
                }
            }
            if(inc[index]==false)
            {
                sequence[i]=track_original[index];//ans sequese array
                inc[index]=true;
                sum=sum+min;
                cheader=track_original[index];
            }
        }

        TextView textView=(TextView)findViewById(R.id.sstfsum);
        String temp="Seek Time="+String.valueOf(sum);
        textView.setText(temp);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sstu);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        FCFSAdapter adapter = new FCFSAdapter(this);

        adapter.setBakingData(sequence);
        adapter.setBakingNumber(ntrack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }



    void scan()
    {

        Integer[] current_header = new Integer[100];
        int k=1;
        int count=0;
        current_header[0]=header;
        if(header >= pre_request)
        {

            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==ntrack-1)
                {
                    current_header[k]=ncylinder-1;
                    k++;
                    break;
                }
                count++;
            }
            if(count==0)
            {
                current_header[k]=ncylinder-1;
                k++;
            }
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {

            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==0)
                {
                    current_header[k]=0;
                    k++;
                    break;
                }
                count++;
            }
            if(count==0)
            {
                current_header[k]=0;
                k++;
            }
            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }

        int seek_time=0;
        for(int i=0;i<=ntrack-1;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);
        }

        TextView textView=(TextView)findViewById(R.id.scansum);
        String temp="Seek Time="+String.valueOf(seek_time);
        textView.setText(temp);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scan);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        FCFSAdapter adapter = new FCFSAdapter(this);

        adapter.setBakingData(current_header);
        adapter.setBakingNumber(ntrack+2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }




    void look()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        current_header[0]=header;

        if(header >= pre_request)
        {

            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {

            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }



        int seek_time=0;

        for(int i=0;i<ntrack;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);

        }
        TextView textView=(TextView)findViewById(R.id.looksum);
        String temp="Seek Time="+String.valueOf(seek_time);
        textView.setText(temp);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.look);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        FCFSAdapter adapter = new FCFSAdapter(this);

        adapter.setBakingData(current_header);
        adapter.setBakingNumber(ntrack+1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }


    void c_scan()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        int count=0;
        current_header[0]=header;
        if(header >= pre_request)
        {


            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==ntrack-1)
                {
                    current_header[k]=ncylinder-1;
                    k++;
                    break;
                }
                count++;
            }
            if(count==0)
            {
                current_header[k]=ncylinder-1;
                k++;
            }

            for(int i=0;i<=he;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {

            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==0)
                {
                    current_header[k]=0;
                    k++;
                    break;
                }
                count++;
            }
            if(count==0)
            {
                current_header[k]=0;
                k++;
            }
            for(int i=ntrack-1;i>=he;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }



        int seek_time=0;

        for(int i=0;i<ntrack+1;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);

        }
        TextView textView=(TextView)findViewById(R.id.cscansum);
        String temp="Seek Time="+String.valueOf(seek_time);
        textView.setText(temp);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cscan);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        FCFSAdapter adapter = new FCFSAdapter(this);

        adapter.setBakingData(current_header);
        adapter.setBakingNumber(ntrack+2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }


    void clook()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        current_header[0]=header;
        if(header >= pre_request)
        {

            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=0;i<=he;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=ntrack-1;i>he;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }
        int seek_time=0;

        for(int i=0;i<ntrack;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);

        }
        TextView textView=(TextView)findViewById(R.id.clooksum);
        String temp="Seek Time="+String.valueOf(seek_time);
        textView.setText(temp);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.clook);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        FCFSAdapter adapter = new FCFSAdapter(this);

        adapter.setBakingData(current_header);
        adapter.setBakingNumber(ntrack+1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
