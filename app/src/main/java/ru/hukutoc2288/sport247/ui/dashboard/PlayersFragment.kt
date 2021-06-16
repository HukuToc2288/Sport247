package ru.hukutoc2288.sport247.ui.dashboard

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.hukutoc2288.sport247.R
import ru.hukutoc2288.sport247.Sports
import ru.hukutoc2288.sport247.readFromFile
import java.io.InputStream


class PlayersFragment : Fragment() {

    private var currentSport: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_players, container, false)
        val playersRecyclerView: RecyclerView = root.findViewById(R.id.players_recycler)
        val assetManager: AssetManager = activity!!.assets
        currentSport = arguments?.getInt(Sports.EXTRA_NAME) ?: Sports.FOOTBALL

        val playersList = ArrayList<PlayerListItem>()
        val playersData =
            (readFromFile(
                if (currentSport == Sports.FOOTBALL) "players_football.csv" else "players_basketball.csv",
                context!!
            )).split("\n")
        for (playerEntry in playersData) {
            val splatEntry = playerEntry.split(Regex.fromLiteral(";"), 2)
            // trailing newline
            if (splatEntry.size < 2) break
            playersList.add(buildPlayerItem(splatEntry[0], splatEntry[1]))
        }
        val adapter = PlayerRecyclerAdapter(context!!, playersList, currentSport)
        playersRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        return root
    }

    fun buildPlayerItem(name: String, imageName: String): PlayerListItem {
        val assetManager: AssetManager = activity!!.assets

        val istr: InputStream =
            assetManager.open((
                    if (currentSport == Sports.FOOTBALL) "football_players_images"
                    else "basketball_players_images")
                    + "/" + imageName)
        val bitmap = BitmapFactory.decodeStream(istr)
        val item = PlayerListItem(name, bitmap)
        return item
    }

    class PlayerRecyclerAdapter(
        private val context: Context,
        private val players: List<PlayerListItem>,
        private val sportType: Int
    ) :
        RecyclerView.Adapter<PlayerRecyclerAdapter.PlayerViewHolder>() {

        class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val playerName: TextView = itemView.findViewById(R.id.player_name)
            val playerSport: TextView = itemView.findViewById(R.id.player_sport)
            val playerImage: ImageView = itemView.findViewById(R.id.player_image)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.player_card, parent, false)
            return PlayerViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
            val item = players[position]
            holder.playerName.text = item.name
            holder.playerImage.setImageBitmap(item.image)
            holder.playerSport.text =
                if (sportType == Sports.FOOTBALL) context.getString(R.string.football)
                else context.getString(R.string.basketball)
        }

        override fun getItemCount(): Int {
            return players.size
        }
    }

    class PlayerListItem(val name: String, val image: Bitmap)
}