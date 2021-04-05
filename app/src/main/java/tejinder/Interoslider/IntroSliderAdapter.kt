package tejinder.Interoslider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.worktodo.Interoslider.IntroSlider
import com.project.todolistproject.R
import com.project.todolistproject.databinding.FragmentWelcomeBinding

class IntroSliderAdapter( var introSlider: List<IntroSlider>):RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewHolder>(){
     class IntroSliderViewHolder(var binding: FragmentWelcomeBinding):RecyclerView.ViewHolder(binding.root){}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
        val binding = DataBindingUtil.inflate<FragmentWelcomeBinding>(LayoutInflater.from(parent.context), R.layout.fragment_welcome,parent,false)
        return IntroSliderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
        holder.binding.welcomeTV.text= introSlider[position].wlcText1
        holder.binding.wlcText1TV.text= introSlider[position].wlcText2
        holder.binding.wlcLogoIG.setImageResource(introSlider[position].wlcLogo)
        holder.binding.wlcText2TV.text=introSlider[position].wlcText3
        holder.binding.wlcText3TV.text=introSlider[position].wlcText4
    }
    override fun getItemCount(): Int
    {
        return introSlider.size
    }
}

