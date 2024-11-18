import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.databinding.ItemHomePetseekerListBinding
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResult
import com.example.petmate.ui.home.petseeker.adapter.PetSeekerListSubAdapter

class PetSeekerListAdapter(
    private val itemList: List<PetSeekerDetailInfoResult>,
    private val isUser: String?
) : RecyclerView.Adapter<PetSeekerListAdapter.PetSeekerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetSeekerListViewHolder {
        val binding = ItemHomePetseekerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetSeekerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetSeekerListViewHolder, position: Int) {
        val petList = itemList.subList(position * 3, (position * 3 + 3).coerceAtMost(itemList.size))
        holder.bind(petList)
    }

    override fun getItemCount(): Int {
        return (itemList.size + 2) / 3  // 3개 단위로 분할하기 때문에 나누기 3
    }

    inner class PetSeekerListViewHolder(private val binding: ItemHomePetseekerListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(petListDivision: List<PetSeekerDetailInfoResult>) {
            Log.d("PetSeekerListAdapter", "isUser: $isUser")

            val adapter = PetSeekerListSubAdapter(petListDivision, isUser)

            // RecyclerView 설정은 한번만 설정되도록 이동
            if (binding.rcvPetseekerList.layoutManager == null) {
                binding.rcvPetseekerList.layoutManager = LinearLayoutManager(binding.rcvPetseekerList.context, LinearLayoutManager.VERTICAL, false)
                binding.rcvPetseekerList.addItemDecoration(VerticalDividerItemDecorator(25))
            }

            binding.rcvPetseekerList.adapter = adapter
        }
    }
}
