package com.mydomain.countries

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CountriesAdapter(
    private val context: Activity,
    private val arrayCountries: JSONArray
) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        try {
            val objectCountry = JSONObject(arrayCountries[i].toString())

            //flag
            val objectFlag= JSONObject(objectCountry.getString("flags"))
            val flag= objectFlag.getString("png")
            Glide.with(context)
                .load(flag)
                .placeholder(R.drawable.ic_preview)
                .error(R.drawable.ic_preview)
                .into(viewHolder.ivFlag)

            //Region | subregion
            val region=objectCountry.getString("region")
            val subregion= objectCountry.getString("subregion")
            val regSub="$region | $subregion"
            viewHolder.tvRegionAndSubRegion.text= regSub

            //Name
            val objectName=JSONObject(objectCountry.getString("name"))
            val name=objectName.getString("common")
            viewHolder.tvName.text=name

            //Native name
            val objectNativeName= JSONObject(objectName.getString("nativeName"))
            val engObject=JSONObject(objectNativeName.getString("eng"))
            val nativeName=engObject.getString("official")
            viewHolder.tvNativeName.text=nativeName

            //Capital
            val arrayCapitals=JSONArray(objectCountry.getString("capital"))
            val capital= arrayCapitals.get(0)
            viewHolder.tvCapital.text=capital.toString()

            //Timezone
            val arrayTimeZone= JSONArray(objectCountry.getString("timezones"))
            val timezone= arrayTimeZone.get(0)
            viewHolder.tvTimezone.text= timezone.toString()

            //Population
            val population=objectCountry.getString("population")
            viewHolder.tvPopulation.text=population

            //area
            val area=objectCountry.getString("area")
            viewHolder.tvArea.text=area






        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return arrayCountries.length()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        var tvRegionAndSubRegion: TextView = itemView.findViewById(R.id.tvRegionAndSubRegion)
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvNativeName: TextView = itemView.findViewById(R.id.tvNativeName)
        var tvCapital: TextView = itemView.findViewById(R.id.tvCapital)
        var tvTimezone: TextView = itemView.findViewById(R.id.tvTimezone)
        var tvPopulation: TextView = itemView.findViewById(R.id.tvPopulation)
        var tvArea: TextView = itemView.findViewById(R.id.tvArea)


    }
}
