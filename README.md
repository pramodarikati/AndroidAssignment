Quick Access Services – Android App (Kotlin | MVVM)
This project implements a Service & Sub-Service Quick Access UI in Android using Kotlin and follows the MVVM (Model-View-ViewModel) architecture.

📱 App Features
✅ Screen 1 – Quick Access Dashboard
Displays a horizontal list of selected sub-services (initially empty).

User can click “Add Services” to open the full list.

Displays each selected sub-service with icon and label.

Supports removing services by reselecting them.

✅ Screen 2 – Service & Sub-Service Selection
A vertical list of expandable/collapsible services using RecyclerView.

Clicking on a service expands/collapses its sub-services.

Sub-services shown as checkboxes.

Max 8 sub-services can be selected.

On clicking "Done", the selected sub-services are returned to Screen 1.
