# Android-View-DSL
The main goal of this project is to explore the abilities of **Kotlin** for making DSL of regular Views.
## How it looks like
In my head it looks like this:
```kotlin
override fun onCreate(bundle: Bundle?) {
    super.onCreate(bundle)
    val textState = mutableStateOf<String>("Hello, World!")
    setContent { // context: [Layout]
        linearLayout(layoutParams().maxSize()) {
            orientation = LinearLayout.VERTICAL

            textView(layoutParams().maxWidth()) {
                text = textState
                textSize = 24.sp
            }
        }
    }

    // somewhere after
    viewModelScope.launch {
        delay(1000L)
        textState.value = "Bye, World!"
    }
}
```
### Generation
Every view except of base interfaces will be generated. So, there will be provided an api with ksp to generate such DSL.
Using the `@ViewDSL` annotation, library will generate interface, implementation for interface (to show only api for end user), function that will automatically add view into view hiarchy and extensions. 
##### Example:
```kotlin
@ViewDSL
typealias TextView = android.widget.TextView
```
Generates:
```kotlin
interface TextView {
    var text: String
    var textSize: Int
    ...
}

fun ViewGroup.textView(layoutParams: LayoutParams = EmptyLayoutParams, block: TextView.() -> Unit) {
    addView(TextViewImpl().apply(block))
}

var TextView.text: State<String>
    private get
    set(value) = state.onUpdate { text = it }

var TextView.textSize: State<Int>
    private get
    set(value) = state.onUpdate { textSize = it }
```
### States
State is just alternative to `Observable<T>` or `StateFlow<T>` in Android-Views DSL System. It just watches for setting value and notifies subscribers about update. Simply interface with `value` property and `onUpdate` function.
There will be builtin integration of `kotlinx.coroutines.StateFlow<T>`. Sample:
```kotlin
textView(/*..*/ ) {
    // ..
    text = viewModel.text.asState(scope)
    // ..
}
```
To disallow state-function generation we may use `@Stateless` annotation.
