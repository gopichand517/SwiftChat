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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
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

// ---------------- Root screen with bottom navigation ----------------

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
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedTab == BottomTab.CHATS,
                    onClick = { selectedTab = BottomTab.CHATS },
                    icon = { Icon(Icons.Default.Chat, contentDescription = "Chats") },
                    label = { Text("Chats") }
                )
                NavigationBarItem(
                    selected = selectedTab == BottomTab.PROFILE,
                    onClick = { selectedTab = BottomTab.PROFILE },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = selectedTab == BottomTab.CONTACTS,
                    onClick = { selectedTab = BottomTab.CONTACTS },
                    icon = { Icon(Icons.Default.People, contentDescription = "Contacts") },
                    label = { Text("Contacts") }
                )
                NavigationBarItem(
                    selected = selectedTab == BottomTab.SETTINGS,
                    onClick = { selectedTab = BottomTab.SETTINGS },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
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
                BottomTab.PROFILE -> SimplePlaceholderScreen("Profile")
                BottomTab.CONTACTS -> SimplePlaceholderScreen("Contacts")
                BottomTab.SETTINGS -> SimplePlaceholderScreen("Settings")
            }
        }
    }
}

// ---------------- Chats screen ----------------

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
            ChatItem("Best Friends Group", "You: I’ll send the pics later", "08:12", 0),
            ChatItem("Maya", "Good night! 🌙", "Yesterday", 1),
            ChatItem("Project Team", "Deadline is next Monday, don’t forget.", "Yesterday", 0),
            ChatItem("Samir", "Let’s play later?", "Sun", 0),
            ChatItem("Mom", "Call me when you’re free ❤️", "Sat", 3)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "SwiftChat",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Chats",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White.copy(alpha = 0.9f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                items(chats) { chat ->
                    ChatRow(chatItem = chat)
                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 0.7.dp,
                        modifier = Modifier.padding(start = 72.dp)
                    )
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
            .clickable { /* later: open chat detail */ }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Simple circular avatar with initials
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF1976D2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chatItem.name.split(" ")
                    .take(2)
                    .map { it.firstOrNull()?.uppercase() ?: "" }
                    .joinToString(""),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = chatItem.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = chatItem.lastMessage,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = chatItem.time,
                fontSize = 11.sp,
                color = Color.Gray
            )
            if (chatItem.unreadCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF0D47A1))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chatItem.unreadCount.toString(),
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ---------------- Simple placeholder screens for other tabs ----------------

@Composable
fun SimplePlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This is a placeholder screen.\nYou can design this later.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
