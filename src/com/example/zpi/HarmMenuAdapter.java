package com.example.zpi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.zpi.Harmonogramy.ViewHolder;
import com.example.zpi.alerts.InternetAlert;
import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;

import java.util.ArrayList;

public class HarmMenuAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
    private String[] naglowek;
    private String[] opis;
    private ArrayList<Harmonogram> harm;
    private boolean brakHarm=false;
    Connect c;
    Harmonogramy harmy;

    public HarmMenuAdapter(Context context, ArrayList<Harmonogram> h){
        mInflater=LayoutInflater.from(context);
        naglowek=naglowki(h);
        harmy = (Harmonogramy) context;
        c=new Connect(context);
        opis=opisy(h);
        harm=h;
    }
    public String[] naglowki(ArrayList<Harmonogram> h){
        String[] naglowki=null;
        String pom = "";
        if(!h.isEmpty()){
            for(int i=0;i<h.size();i++){
                switch(h.get(i).getModul()){
                    case 0:
                        pom+="Woda/";
                        break;
                    case 1:
                        pom+="Roleta/";
                        break;
                    case 4:
                        pom+="Oświetlenie/";
                        break;
                    default:
                        pom+="Brak Harmonogramów/";
                        brakHarm=true;
                        break;
                }
            }
            naglowki=pom.split("/");
        }
        return naglowki;
    }
    public String[] opisy(ArrayList<Harmonogram> h){
        String[] opisy=null;
        String pom = "";
        if(!h.isEmpty()){
            for(int i=0;i<h.size();i++){
                pom+=h.get(i).getCzasStart()+"-";
                pom+=h.get(i).getCzasStop()+" ";
                if(!(h.get(i).getValStart().equals("true")||h.get(i).getValStart().equals("false"))){
                    switch (h.get(i).getModul()){
                        case 0:
                            pom+=h.get(i).getValStart()+"C"+" ";
                            break;
                        case 1:
                            pom+=h.get(i).getValStart()+"%"+" ";
                            break;
                        case -1:
                            pom+="Dodaj harmonogram";
                    }
                }
                pom+=dni(h.get(i).getDni());
                pom+="/";
            }
            opisy=pom.split("/");
        }
        return opisy;
    }
    public String dni(String d){
        String dni="";

        String[] dniSkrot={"Pon.,","Wt.,","Sr.,","Cz.,","Pt.,","Sob.,","Nd.,"};
        String[] dniPelne={"Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota","Niedziela","Codziennie"};

        if(!d.equals("null")){
            char[] pom=d.toCharArray();
        if(pom.length==1)
            dni=dniPelne[Integer.valueOf(String.valueOf(pom[0]))];
        else if(pom.length==7)
            dni=dniPelne[7];
        else{
            for(int i=0;i<pom.length;i++){
                dni+=dniSkrot[Integer.valueOf(String.valueOf(pom[i]))-1];
            }
        }
        }
        return dni;
    }
    public Harmonogram getHarmonogram(int position){
        return harm.get(position);
    }
    public int getCount() {
        return naglowek.length;
    }
    public void setHarmonogram(Harmonogram h,int position){
        harm.remove(position);
        harm.add(position,h);
        try {
            Log.d("idUsun",""+h.getId());
            c.requestDelHarm(h.getId());
            c.requestSetHarm(h);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();
            //InternetAlert internetAlert=new InternetAlert();
            //internetAlert.zwrocAlert();
        }
    }

    public Object getItem(int position) {
        return position;
    }
    public String getNaglowek(int position){
        return naglowek[position];
    }
    public String getOpis(int position){
        return opis[position];
    }

    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    final ViewGroup rodzic=parent;
    final ToggleButton btn;
    final int pos=position;
    if (convertView == null) {
        convertView = mInflater.inflate(R.layout.harm_menu_adapter, null);
        holder = new ViewHolder();
        holder.text = (TextView) convertView.findViewById(R.id.harmNagl);
        holder.text2=(TextView) convertView.findViewById(R.id.harmOpis);
        btn=(ToggleButton) convertView.findViewById(R.id.HarmMenuWl);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Harmonogram pom=getHarmonogram(pos);
                pom.setWl(btn.isChecked());
                setHarmonogram(pom, pos);
                harmy.onResume();
                if(btn.isChecked())
                    Toast.makeText(rodzic.getContext(), "Uruchomiono harmonogram", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(rodzic.getContext(), "Wyłączono harmonogram", Toast.LENGTH_SHORT).show();
            }
        });
        //if(brakHarm)
            btn.setEnabled(!brakHarm);

        btn.setFocusable(false);
        convertView.setTag(holder);

    } 
    else {
        btn=(ToggleButton) convertView.findViewById(R.id.HarmMenuWl);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Harmonogram pom=getHarmonogram(pos);
                pom.setWl(btn.isChecked());
                setHarmonogram(pom, pos);
                harmy.onResume();
                if(btn.isChecked())
                    Toast.makeText(rodzic.getContext(), "Uruchomiono harmonogram", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(rodzic.getContext(), "Wyłączono harmonogram", Toast.LENGTH_SHORT).show();
            }
        });
        holder = (ViewHolder) convertView.getTag();

    	}
        holder.text.setText(naglowek[position]);
        holder.text2.setText(opis[position]);
        btn.setChecked(harm.get(position).isWl());


        convertView.setBackgroundColor(0xFFFFFFF);
    return convertView;
    }
}

