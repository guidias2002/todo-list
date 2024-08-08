CREATE TABLE tasks (
    id UUID PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    accomplished BOOLEAN NOT NULL,
    priority INTEGER NOT NULL
);