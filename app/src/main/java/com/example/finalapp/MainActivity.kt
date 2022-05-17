package com.example.finalapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.*
import kotlinx.android.synthetic.main.item_todo.view.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter : TodoAdapter
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationID = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        createNotificationChannel()

        var files: Array<File> = filesDir.listFiles()
        for (i in files){
            val todo = Todo(i.name)
            i.writeText("true")
            todoAdapter.addTodo(todo)
        }

        btnAddTodo.setOnClickListener {

            val todoTitle = etTodoTitle.text.toString()
            //val alarmTitle = eaTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()){
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)

                val file = File(filesDir, todoTitle)
                file.writeText("true")

                sendNotification()
                etTodoTitle.text.clear()

                //eaTodoTitle.text.clear()
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
            val todos = todoAdapter.getTodos()
            for(i in todos){
                for(j in files) {
                    if(i.title == j.name){
                        j.writeText("false")
                    }
                }
            }
            for (i in files){
                if(i.readText() == "true"){ i.delete() }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //val bitmap = BitmapFactory.decodeResource(applicationContext.resources, androidx.drawerlayout.R.drawable.notification_icon_background)

        val todoTitle = etTodoTitle.text.toString()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Reminder")
            .setContentText(todoTitle)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationID, builder.build())
        }

    }
}
