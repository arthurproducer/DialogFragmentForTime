package br.com.fiap.dialogfragmentfortime

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NoteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DialogInterface.OnClickListener { _, i ->
            if(i == DialogInterface.BUTTON_NEGATIVE){
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.google.com.br"))
                startActivity(intent)
            }
        }
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.title)
            .setMessage(R.string.message)
            .setPositiveButton(android.R.string.ok,null)
            .setNegativeButton(R.string.btNegative,listener)
            .create()


    }
}