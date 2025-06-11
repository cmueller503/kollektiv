# **kollektiv**

Eine intelligente ToDo-App für die ganzheitliche Produktivitätssteigerung

###Kurzbeschreibung:

*kollektiv* ist eine fortschrittliche ToDo-App, die es Benutzern ermöglicht, ihre täglichen Aufgaben und Projekte effektiv zu verwalten. Dank der Integration von modernen Technologien bietet die Anwendung eine benutzerfreundliche Oberfläche, die sowohl auf mobilen als auch auf webbasierten Plattformen verfügbar ist. *kollektiv* ist darauf ausgerichtet, die Produktivität der Benutzer durch intelligente Features und benutzerdefinierte Optionen zu steigern.

###Zentrale Anwendungsfälle:

1. **Projektmanagement:** Benutzer können ihre Aufgaben innerhalb von Projekten organisieren. Jedes Projekt kann verschiedenen Kategorien zugeordnet werden, um eine präzise Zuordnung zu ermöglichen.

2. **Multi-User-Fähigkeit:** *kollektiv* unterstützt Mehrbenutzer-Interaktion, sodass Gruppen oder Teams Aufgaben gemeinsam bearbeiten und sich gegenseitig unterstützen können.

3. **Zuweisung von Aufgaben:** Aufgaben können einzelnen Benutzern oder Gruppen zugewiesen werden, wodurch die Verantwortung klar definiert und die Überprüfung der Arbeitsschritte erleichtert wird.

4. **Sicherheit und Zugriffsberechtigungen:** *kollektiv* gewährleistet, dass Benutzer nur ihre eigenen Aufgaben sehen und bearbeiten können. Zugriffsberechtigungen werden basierend auf den Projektmitgliedern oder der Gruppenzugehörigkeit festgelegt, was eine sichere und effiziente Zusammenarbeit ermöglicht.

5. **Intelligente Filtermöglichkeiten:** Die Anwendung bietet intelligente Filterfunktionen, die es Benutzern ermöglichen, Aufgaben nach verschiedenen Kriterien wie Fälligkeit, Priorität und Kategorie zu durchsuchen.

6. **Status- und Prioritätsmanagement:** Aufgaben können in verschiedenen Statuszuständen verwalten werden, z.B. offen, in Bearbeitung oder abgeschlossen. Die Priorisierung der Aufgaben hilft Benutzern, kritische Aufgaben zu identifizieren und zu priorisieren.

###Zweck der Anwendung:

Der Hauptzweck von *kollektiv* besteht darin, den Benutzern eine zentrale Plattform zu bieten, auf der sie ihre täglichen Aufgaben und Projekte effektiv verwalten können. Die Anwendung ist darauf ausgerichtet, sowohl Einzelpersonen als auch Teams dabei zu unterstützen, Zeit effizient zu nutzen und Produktivität zu steigern. Dank der benutzerfreundlichen Oberfläche und der zahlreichen Funktionen erlangt der Benutzer eine kontrolliertere, effizientere und zufriedenstellendere Erfahrung bei der Verwaltung seiner Aufgaben.

Mit *kollektiv* ist das Ziel, jeden Tag einfacher und effizienter zu gestalten, indem die Aufgaben und Projekte intelligent und zielgerichtet verwaltet werden.

### Benutzer:
- **UserID**: Primärschlüssel
- **Username**: Benutzername (Eindeutig)
- **Email**: E-Mail des Benutzers
- **Password**: Passwort des Benutzers

### Gruppe:
- **GroupID**: Primärschlüssel
- **GroupName**: Name der Gruppe
- **Members**: Liste der Benutzer, die der Gruppe angehören

### Projekt:
- **ProjectID**: Primärschlüssel
- **ProjectName**: Name des Projekts
- **Members**: Liste der Benutzer, die dem Projekt angehören
- **Tasks**: Liste der Aufgaben, die dem Projekt zugewiesen sind

### Aufgabe:
- **TaskID**: Primärschlüssel
- **TaskName**: Name der Aufgabe
- **Description**: Beschreibung der Aufgabe
- **DueDate**: Fälligkeitsdatum der Aufgabe
- **Status**: Status der Aufgabe (z.B. "offen", "in Bearbeitung", "abgeschlossen")
- **Priority**: Priorität der Aufgabe (z.B. "hoch", "mittel", "niedrig")
- **Category**: Kategorie der Aufgabe (z.B. "Projektmanagement", "Entwicklung", "Design")
- **ProjectID**: Fremdschlüssel zum Projekt
- **Assignee**: Benutzer, der die Aufgabe zugewiesen wurde (optional)

### Zugriffsberechtigung:
- **UserID**: Fremdschlüssel zum Benutzer
- **ProjectID**: Fremdschlüssel zum Projekt
- **Role**: Rolle des Benutzers im Projekt (z.B. "Projektleiter", "Entwickler", "Designer")

### Entitäten und deren Beziehungen:
1. Ein Benutzer kann mehreren Gruppen angehören.
2. Ein Projekt kann mehreren Benutzern zugewiesen sein.
3. Eine Aufgabe kann mehreren Projekten zugewiesen werden.
4. Ein Benutzer kann mehreren Aufgaben zugewiesen werden.
5. Ein Benutzer kann nur seine eigenen Aufgaben sehen.
6. Ein Benutzer kann nur Aufgaben sehen, deren Projekt ihm zugewiesen ist oder der Gruppe, der er angehört.

### Datenbanktabellen:
- **Users**: UserID, Username, Email, Password
- **Groups**: GroupID, GroupName
- **Projects**: ProjectID, ProjectName
- **Tasks**: TaskID, TaskName, Description, DueDate, Status, Priority, Category, ProjectID, Assignee
- **GroupMembers**: UserID (Fremdschlüssel zu Users), GroupID (Fremdschlüssel zu Groups)
- **ProjectMembers**: UserID (Fremdschlüssel zu Users), ProjectID (Fremdschlüssel zu Projects)
- **ProjectTasks**: ProjectID (Fremdschlüssel zu Projects), TaskID (Fremdschlüssel zu Tasks)
- **UserTasks**: UserID (Fremdschlüssel zu Users), TaskID (Fremdschlüssel zu Tasks)
- **AccessPermissions**: UserID (Fremdschlüssel zu Users), ProjectID (Fremdschlüssel zu Projects), Role

### Fluss der Daten:
1. Ein Benutzer meldet sich mit Benutzername und Passwort an.
2. Die Anwendung prüft, ob die Anmeldeinformationen korrekt sind.
3. Wenn der Benutzer angemeldet ist, kann er seine Aufgaben sehen, die ihm zugewiesen sind oder die zu Projekten gehören, denen er angehört.
4. Der Benutzer kann neue Aufgaben erstellen und diese Projekten zuweisen.
5. Der Benutzer kann Aufgaben bearbeiten oder löschen.
6. Gruppenleiter können Aufgaben anderen Benutzern zuweisen.

Das grundlegende Konzept ist auf diese Weise aufgebaut, um sicherzustellen, dass die Anforderungen der Benutzer und die Flexibilität der Anwendung berücksichtigt werden. Du kannst weitere Funktionen und Features hinzufügen, wie z.B. Aufgaben-Reminder, Integration mit externen Tools, usw.