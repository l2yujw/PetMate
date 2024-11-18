import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemHomeShelterpetInfoBinding
import com.example.petmate.ui.home.petseeker.shelterpetInfo.data.ShelterpetInfoData

class ShelterPetInfoAdapter(private val itemList: List<ShelterpetInfoData>) : RecyclerView.Adapter<ShelterPetInfoAdapter.ShelterPetInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterPetInfoViewHolder {
        val binding = ItemHomeShelterpetInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShelterPetInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShelterPetInfoViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ShelterPetInfoViewHolder(private val binding: ItemHomeShelterpetInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShelterpetInfoData) {
            binding.abandonedinfoAdditionalinfoTag.text = item.info_tag
            binding.abandonedinfoAdditionalinfoInfo.text = item.info_info
        }
    }
}
