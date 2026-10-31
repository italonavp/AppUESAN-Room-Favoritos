package com.example.appuesan.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage


object SupabaseManager {

    private const val SUPABASE_URL = "https://iqizidkgspqocgwhwyct.supabase.co"

    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlxaXppZGtnc3Bxb2Nnd2h3eWN0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTIyNjM2MDQsImV4cCI6MjEwNzgzOTYwNH0.WKiCFTU_N5ksOfj9NkHg2_TPO8_hOlQ6UG89ZgzK7zU"

    val client = createSupabaseClient(
        SUPABASE_URL, SUPABASE_KEY)
    {
        install(Storage)
    }
}