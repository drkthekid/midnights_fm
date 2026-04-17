# Entity Relationship Model

This document describes the database structure based on the diagram used in this application

### 1. Table `users`

| Column       | Type/Constraint  | Description                 |
|:-------------|:-----------------|:----------------------------|
| `id`         | PK (Primary Key) | User's identifier           |
| `username`   | Attribute        | User's username             |
| `email`      | UNIQUE           | User's email address        |
| `password`   | NOT NULL         | User's credentials          |
| `role`       | Attribute        | User's role in the system   |
| `created_at` | Attribute        | User's create in the system |

### 2. Table `tracks`

| Column      | Type/Constraint  | Description        |
|:------------|:-----------------|:-------------------|
| `id`        | PK (Primary Key) | Track's identifier |
| `name`      | Attribute        | Track's name       |
| `artist_id` | FK (FOREIGN KEY) | Track's artists    |
| `feats_id`  | FK (FOREIGN KEY) | Track's feats      |
| `album_id`  | FK (FOREIGN KEY) | Track's album      |

### 3. Table `albums`

| Column      | Type/Constraint  | Description                         |
|:------------|:-----------------|:------------------------------------|
| `id`        | PK (Primary Key) | Album's identifier                  |
| `name`      | Attribute        | Album's name                        |
| `genre`     | Attribute        | Album's genre                       |
| `artist_id` | FK (FOREIGN KEY) | Album's artists                     |
| `tracks_id` | FK (FOREIGN KEY) | Album's registered tracks on system |

### 4. Table `reviews`

| Column       | Type/Constraint  | Description                     |
|:-------------|:-----------------|:--------------------------------|
| `id`         | PK (Primary Key) | Review's identifier             |
| `commentary` | Attribute        | Review's review                 |
| `assessment` | Attribute        | Review's assessment (1,5) stars |
| `album_id`   | FK (FOREIGN KEY) | Review's album                  |
| `user_id`    | FK (FOREIGN KEY) | Review's user                   |

### 5. Table `obsessions`

| Column        | Type/Constraint  | Description             |
|:--------------|:-----------------|:------------------------|
| `id`          | PK (Primary Key) | Obsession's identifier  |
| `description` | Attribute        | Obsession's description |
| `user_id`     | FK (FOREIGN KEY)       | Obsession's owner       |
| `album_id`    | FK (FOREIGN KEY) | Obsession's album       |
| `track_id`    | FK (FOREIGN KEY) | Obsession's track       |

### 6. Table `playlist_invites`

| Column        | Type/Constraint  | Description                                 |
|:--------------|:-----------------|:--------------------------------------------|
| `id`          | PK (Primary Key) | Invite's identifier                         |
| `playlist_id` | FK (FOREIGN KEY)      | Invite's playlist                           |
| `user_id`     | FK (FOREIGN KEY)         | Invite's owner (sender)                     |
| `resolver_id` | FK (FOREIGN KEY)        | Invite's resolver                           |
| `status`      | Attribute        | Invite's status (PENDING, ACCEPT, REJECTED) |

---

## Relationships

### progress . . .