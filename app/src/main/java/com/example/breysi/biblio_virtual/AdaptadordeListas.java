package com.example.breysi.biblio_virtual;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by breysi on 01/05/2018.
 */

public class AdaptadordeListas extends BaseAdapter {
    public ArrayList<Libro> listaLi = new ArrayList<Libro>();
    private LayoutInflater l_Inflater;
    Context context;

    public AdaptadordeListas(Context context, ArrayList<Libro> results) {
        listaLi = results;
        l_Inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public int getCount() {
        return listaLi.size();
    }

    @Override
    public Libro getItem(int posicion) {
        return listaLi.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.lista_libro, null);
           /* holder = new ViewHolder();
            holder.Image = (ImageView) convertView.findViewById(R.id.imageView_libro);
            holder.MsgType = (TextView) convertView.findViewById(R.id.msgtype1);
*/




        } else {
            //holder = (ViewHolder) convertView.getTag();
        }

        Libro libro = getItem(i);
        ImageView iv_libro = (ImageView) convertView.findViewById(R.id.imageView_libro);
        TextView tv_titulo = (TextView) convertView.findViewById(R.id.tv_titulo);
        TextView tv_autor = (TextView) convertView.findViewById(R.id.tv_autor);


        try{
            Picasso.with(context).load(libro.getPortada()).placeholder(R.mipmap.ic_launcher).into(iv_libro);
        }catch (Exception e){
            e.printStackTrace();
        }
        tv_titulo.setText(libro.getTitulo());
        tv_autor.setText(libro.getAutor());
        return convertView;
    }

    // holder view for views
    static class ViewHolder {
        ImageView Image;
        TextView MsgType;
    }

}
