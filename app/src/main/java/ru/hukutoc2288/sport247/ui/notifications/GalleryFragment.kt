package ru.hukutoc2288.sport247.ui.notifications

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.hukutoc2288.sport247.R
import ru.hukutoc2288.sport247.Sports
import ru.hukutoc2288.sport247.readFromFile
import java.io.InputStream

class GalleryFragment : Fragment() {

    private var currentSport: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val galleryRecyclerView: RecyclerView = root.findViewById(R.id.gallery_recycler)
        currentSport = arguments?.getInt(Sports.EXTRA_NAME) ?: Sports.FOOTBALL


        val galleryList = ArrayList<GalleryListItem>()
        val path = if (currentSport == Sports.FOOTBALL) "football_images" else "basketball_images"
        val galleryPathsList = activity!!.assets.list(path)
        for (photoEntry in galleryPathsList!!){
            galleryList.add(buildGalleryItem("$path/$photoEntry"))
        }
        val adapter = GalleryRecyclerAdapter(galleryList)
        galleryRecyclerView.layoutManager = LinearLayoutManager(context)
        galleryRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        return root
    }

    fun buildGalleryItem(imageName: String): GalleryListItem{
        val assetManager: AssetManager = activity!!.assets

        val istr: InputStream = assetManager.open(imageName)
        val bitmap = BitmapFactory.decodeStream(istr)
        val item = GalleryListItem(bitmap)
        return item
    }

    class GalleryRecyclerAdapter(private val gallery: List<GalleryListItem>) :
        RecyclerView.Adapter<GalleryRecyclerAdapter.GalleryViewHolder>() {

        class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image: ImageView = itemView.findViewById(R.id.image)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
            return GalleryViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
            val item = gallery[position]
            holder.image.setImageBitmap(item.image)
        }

        override fun getItemCount(): Int {
            return gallery.size
        }
    }

    class GalleryListItem(val image: Bitmap)
}