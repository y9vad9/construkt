# Construkt

The main goal of this project is to explore the abilities of **Kotlin** for making DSL of regular Views.
> Just playground. Not for prod.

## How it looks like

```kotlin
class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentScreen = MutableStateFlow<Screen>(Screen.Home)
            linearLayout(linearLayoutParams().maxSize()) {
                orientation(LinearLayout.VERTICAL)
                linearLayout(linearLayoutParams().maxWidth().weight(1f)) {
                    orientation(LinearLayout.VERTICAL)
                    gravity(Gravity.CENTER)
                    currentScreen.constructOnEach {
                        when (it) {
                            is Screen.Home -> HomeScreen()
                            is Screen.About -> AboutScreen()
                        }
                    }
                }

                bottomNavigationView(linearLayoutParams().maxWidth()) {
                    val color = ColorStateList(android.R.attr.state_checked to Color.WHITE, default = Color.GRAY)
                    itemIconTintList(color)
                    itemTextColor(color)
                    labelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED)

                    menu {
                        item("Home", id = 1).setIcon(R.drawable.ic_outline_home_24)
                        item("About", id = 2).setIcon(R.drawable.ic_outline_info_24)
                    }

                    onItemSelectedListener {
                        when (it.itemId) {
                            1 -> currentScreen.value = Screen.Home
                            2 -> currentScreen.value = Screen.About
                        }
                        true
                    }
                }
            }
        }
    }
}
```
[Full code here](example/src/main/java/com/construkt/example/app/AppView.kt)

[![Example](assets/videos/example.mp4)](assets/videos/example.mp4)

### Generation

Every view except of base interfaces will be generated. So, there is provided an api with ksp to generate such DSL.
Using the `@ViewDSL` annotation, library will generate interface, implementation for interface (to show only api for end
user), function that will automatically add view into view hierarchy and extensions.

##### Example:

```kotlin
@ViewDSL
typealias TextView = android.widget.TextView
```

Generates:

```kotlin
interface TextView {
    fun text(text: String)
    fun textSize(textSize: Int)
    ...
}

fun ViewGroup.textView(layoutParams: LayoutParams? = null, block: TextView.() -> Unit) {
    addView(TextViewImpl().apply(block))
}

fun ViewGroup.textView(layoutParams: State<LayoutParams>, block: TextView.() -> Unit) {
    addView(TextViewImpl().apply(block))
}

fun TextView.text(text: State<String>) {
    lifecycleOwner.lifecycleScope.launch {
        text.collect { text(it) }
    }
}

fun TextView.textSize(textSize: State<Int>) {
    lifecycleOwner.lifecycleScope.launch {
        textSize.collect { textSize(it) }
    }
}
```
