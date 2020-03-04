package com.ore.contactapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.ContactViewBinding

/** THIS CLASS PROVIDES/INFLATES A UNIT TEMPLATE OF WHAT EACH CONTACT DISPLAYED IN A RECYCLERVIEW LIST WOULD LOOK LIKE **/
class ContactsAdapter(private var contacts: List<Contact>, private var context: Context) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    private lateinit var binding: ContactViewBinding
    private val inflater = LayoutInflater.from(context)

    inner class ContactViewHolder(contactView: ContactViewBinding) :
        RecyclerView.ViewHolder(contactView.root) {
        fun bind(data: Contact) {
            binding.contact = data
        }

        var contactName: TextView = contactView.contactName
        var contactEmail: TextView = contactView.contactEmail
        var contactPhone: TextView = contactView.contactPhone
        var contactCall: ImageView = contactView.contactCall
        var contactImage: ImageView = contactView.contactImageTwo
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        binding = ContactViewBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
//        val contactView = inflater.inflate(R.layout.contact_view, parent,false)
//        return ContactViewHolder(contactView)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contacts[position])
        Glide.with(binding.root.context).asBitmap()
            .load("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
            .centerCrop().fallback(
            R.drawable.ic_contact_logo_main
        ).error(R.drawable.ic_add_contact_logo).placeholder(R.drawable.ic_contact_logo_main)
            .into(holder.contactImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MainViewContactActivity::class.java)
            intent.putExtra("CONTACT", contact)
            context.startActivity(intent)
        }

        holder.contactCall.setOnClickListener {
            val number = holder.contactPhone.text.toString().trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(number)))
            try {
                startActivity(holder.contactPhone.context, intent, null)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}