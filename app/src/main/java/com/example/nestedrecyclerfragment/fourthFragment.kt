package com.example.nestedrecyclerfragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class fourthFragment : Fragment() {
    lateinit var btnSendEmail: Button
    lateinit var etMessage: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_fourth, container, false)
        btnSendEmail = v.findViewById(R.id.btnSendEmail)
        etMessage = v.findViewById(R.id.etMessage)

        btnSendEmail.setOnClickListener{sendEmail()}
        return v
    }

    fun sendEmail(){
        val recipient = getString(R.string.Support_Email)
        val subject = "Support Email"
        val message = etMessage.text.toString()

        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            //start email intent
            startActivity(mIntent)
            //Toast.makeText(context, "Email Sent", Toast.LENGTH_LONG).show()
            etMessage.setText("")
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }


    }


}
