# Entity Relationship Model

This document describes the database structure based on the diagram used in this application.

---

### 1. Table `users`

| Column       | Type/Constraint  | Description                |
|:-------------|:-----------------|:---------------------------|
| `id`         | PK (Primary Key) | User identifier            |
| `username`   | Attribute        | Username                   |
| `email`      | UNIQUE           | Email address              |
| `password`   | NOT NULL         | User password (encrypted)  |
| `role`       | Attribute        | Role in the system         |

---

### 2. Table `tracks`

| Column      | Type/Constraint  | Description              |
|:------------|:-----------------|:-------------------------|
| `id`        | PK (Primary Key) | Track identifier         |
| `name`      | Attribute        | Track name               |
| `artist_id` | FK (FOREIGN KEY) | Main artist              |
| `album_id`  | FK (FOREIGN KEY) | Related album            |

---

### 3. Table `albums`

| Column      | Type/Constraint  | Description              |
|:------------|:-----------------|:-------------------------|
| `id`        | PK (Primary Key) | Album identifier         |
| `name`      | Attribute        | Album name               |
| `genre`     | Attribute        | Album genre              |
| `artist_id` | FK (FOREIGN KEY) | Album owner (artist)     |

---

### 4. Table `track_feats`

| Column      | Type/Constraint  | Description              |
|:------------|:-----------------|:-------------------------|
| `track_id`  | FK (FOREIGN KEY) | Track reference          |
| `artist_id` | FK (FOREIGN KEY) | Featured artist          |

---

### 5. Table `reviews`

| Column       | Type/Constraint  | Description              |
|:-------------|:-----------------|:-------------------------|
| `id`         | PK (Primary Key) | Review identifier        |
| `commentary` | Attribute        | Review content           |
| `assessment` | Attribute        | Rating (1–5 stars)       |
| `album_id`   | FK (FOREIGN KEY) | Reviewed album           |
| `user_id`    | FK (FOREIGN KEY) | Review author            |

---

### 6. Table `obsessions`

| Column        | Type/Constraint  | Description              |
|:--------------|:-----------------|:-------------------------|
| `id`          | PK (Primary Key) | Obsession identifier     |
| `description` | Attribute        | Description              |
| `user_id`     | FK (FOREIGN KEY) | Owner                    |
| `album_id`    | FK (FOREIGN KEY) | Related album (optional) |
| `track_id`    | FK (FOREIGN KEY) | Related track (optional) |

---

### 7. Table `playlist_invites`

| Column        | Type/Constraint  | Description                         |
|:--------------|:-----------------|:------------------------------------|
| `id`          | PK (Primary Key) | Invite identifier                   |
| `playlist_id` | FK (FOREIGN KEY) | Target playlist                     |
| `user_id`     | FK (FOREIGN KEY) | Sender                              |
| `resolver_id` | FK (FOREIGN KEY) | Receiver                            |
| `status`      | Attribute        | Status (PENDING, ACCEPT, REJECTED)  |

---

### 8. Table `refresh_tokens`

| Column       | Type/Constraint  | Description                     |
|:-------------|:-----------------|:--------------------------------|
| `id`         | PK (Primary Key) | Refresh token identifier        |
| `user_id`    | FK (FOREIGN KEY) | Token owner                     |
| `expires_at` | Attribute        | Expiration date/time            |
| `revoked`    | Attribute        | Indicates if token is revoked   |

## Relationships

- User **1:N** Album
- User **1:N** Track
- Album **1:N** Track
- Track **N:N** Artist (via `track_feats`)
- User **1:N** Review
- Album **1:N** Review
- User **1:N** Obsession  
- User **1:N** RefreshToken