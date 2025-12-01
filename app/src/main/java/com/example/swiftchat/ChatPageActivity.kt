package com.example.swiftchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiftchat.ui.theme.SwiftChatTheme

class ChatPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwiftChatTheme {
                ChatPageRoot()
            }
        }
    }
}

enum class BottomTab {
    CHATS, PROFILE, CONTACTS, SETTINGS
}

@Composable
fun ChatPageRoot() {
    var selectedTab by remember { mutableStateOf(BottomTab.CHATS) }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0D47A1),
            Color(0xFF1976D2)
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = selectedTab == BottomTab.CHATS,
                    onClick = { selectedTab = BottomTab.CHATS },
                    icon = { Icon(Icons.Default.Chat, contentDescription = null) },
                    label = { Text("Chats") }
                )
                NavigationBarItem(
                    selected = selectedTab == BottomTab.PROFILE,
                    onClick = { selectedTab = BottomTab.PROFILE },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = selectedTab == BottomTab.CONTACTS,
                    onClick = { selectedTab = BottomTab.CONTACTS },
                    icon = { Icon(Icons.Default.People, contentDescription = null) },
                    label = { Text("Contacts") }
                )
                NavigationBarItem(
                    selected = selectedTab == BottomTab.SETTINGS,
                    onClick = { selectedTab = BottomTab.SETTINGS },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                BottomTab.CHATS -> ChatsScreen()
                BottomTab.PROFILE -> ProfileScreen()
                BottomTab.CONTACTS -> ContactsScreen()
                BottomTab.SETTINGS -> SettingsScreen()
            }
        }
    }
}

////////////////////////////////////////////////////////////////
// CHATS SCREEN (Already good)
////////////////////////////////////////////////////////////////

data class ChatItem(
    val name: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0
)

@Composable
fun ChatsScreen() {
    val chats = remember {
        listOf(
            ChatItem("Alex Johnson", "Hey, are we still meeting today?", "09:45", 2),
            ChatItem("Best Friends Group", "You: I'll send the pics later", "08:12"),
            ChatItem("Maya", "Good night! 🌙", "Yesterday", 1),
            ChatItem("Project Team", "Deadline is Monday", "Yesterday"),
            ChatItem("Samir", "Let's play later?", "Sun"),
            ChatItem("Mom", "Call me when free ❤️", "Sat", 3)
        )
    }

    Column {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("SwiftChat", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Chats", fontSize = 18.sp, color = Color.White.copy(.85f))
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(.9f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            LazyColumn {
                items(chats) { chat ->
                    ChatRow(chat)
                    Divider(color = Color(0xFFE0E0E0), thickness = 0.7.dp, modifier = Modifier.padding(start = 72.dp))
                }
            }
        }
    }
}

@Composable
fun ChatRow(chatItem: ChatItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF1976D2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chatItem.name.split(" ").map { it.first().uppercase() }.joinToString(""),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(chatItem.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(chatItem.lastMessage, fontSize = 13.sp, color = Color.Gray, maxLines = 1)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(chatItem.time, fontSize = 11.sp, color = Color.Gray)
            if (chatItem.unreadCount > 0) {
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF0D47A1))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("${chatItem.unreadCount}", color = Color.White, fontSize = 11.sp)
                }
            }
        }
    }
}

////////////////////////////////////////////////////////////////
// PROFILE SCREEN – REAL DATA
////////////////////////////////////////////////////////////////

@Composable
fun ProfileScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White.copy(alpha = 0.95f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0D47A1)),
                contentAlignment = Alignment.Center
            ) {
                Text("GC", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            Text("GopiChand", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
            Text("gopichand123@gmail.com", fontSize = 16.sp, color = Color.Gray)

            Spacer(Modifier.height(32.dp))

            ProfileOption("Edit Profile")
            ProfileOption("Change Password")
            ProfileOption("Privacy")
        }
    }
}

@Composable
fun ProfileOption(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        }
    }
}

////////////////////////////////////////////////////////////////
// CONTACTS SCREEN – SAMPLE DATA
////////////////////////////////////////////////////////////////

@Composable
fun ContactsScreen() {
    val contacts = listOf(
        "Arjun Reddy",
        "Meera Sharma",
        "Rohit Das",
        "Kavya S",
        "Teja Kumar",
        "Nikhil R",
        "Sangeeta",
        "Rama Krishna"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White.copy(alpha = 0.95f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(contacts) { name ->
                ContactRow(name)
            }
        }
    }
}

@Composable
fun ContactRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF1976D2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.split(" ").map { it.first().uppercase() }.joinToString(""),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

////////////////////////////////////////////////////////////////
// SETTINGS SCREEN – SAMPLE SETTINGS
////////////////////////////////////////////////////////////////

@Composable
fun SettingsScreen() {
    val settings = listOf(
        "Notifications",
        "Account",
        "Appearance",
        "Help & Support",
        "About SwiftChat"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White.copy(alpha = 0.95f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(settings) { item ->
                SettingsRow(item)
            }
        }
    }
}

@Composable
fun SettingsRow(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}
