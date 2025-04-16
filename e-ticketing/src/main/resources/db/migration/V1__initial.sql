-- ENUMS
CREATE TYPE residency AS ENUM ('LOCAL', 'INTERNATIONAL');
CREATE TYPE currency AS ENUM ('ETB', 'USD', 'EURO'); -- extend if needed
CREATE TYPE ticket_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED'); -- example statuses

-- TICKET TYPES
CREATE TABLE ticket_types (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              description TEXT,
                              base_price DOUBLE PRECISION,
                              available BOOLEAN DEFAULT TRUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PRICE CONFIGS
CREATE TABLE price_configs (
                               id SERIAL PRIMARY KEY,
                               ticket_type_id INTEGER REFERENCES ticket_types(id) ON DELETE CASCADE,
                               residency residency NOT NULL,
                               currency currency NOT NULL,
                               price DOUBLE PRECISION NOT NULL,
                               active BOOLEAN DEFAULT TRUE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE (ticket_type_id, residency)
);

-- INVENTORIES
CREATE TABLE inventories (
                             id SERIAL PRIMARY KEY,
                             event_id BIGINT NOT NULL,
                             total INTEGER,
                             available INTEGER,
                             reserved INTEGER
);

-- TIME SLOTS
CREATE TABLE timeslots (
                           id SERIAL PRIMARY KEY,
                           start_time TIME NOT NULL,
                           end_time TIME NOT NULL,
                           is_active BOOLEAN DEFAULT TRUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- VISIT SCHEDULES
CREATE TABLE visits_schedules (
                                  id SERIAL PRIMARY KEY,
                                  date DATE NOT NULL,
                                  is_open BOOLEAN DEFAULT TRUE,
                                  reason_for_closing TEXT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- QUEUE ENTRIES
CREATE TABLE queue_entries (
                               id SERIAL PRIMARY KEY,
                               visit_schedule_id INTEGER REFERENCES visits_schedules(id) ON DELETE CASCADE,
                               time_slot_id INTEGER REFERENCES timeslots(id) ON DELETE CASCADE,
                               queue_position INTEGER,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- VISITORS (Assumed)
CREATE TABLE visitors (
                          id SERIAL PRIMARY KEY,
                          full_name VARCHAR(150),
                          email VARCHAR(150),
                          phone_number VARCHAR(50)
    -- Add other visitor fields here
);

-- TICKETS
CREATE TABLE tickets (
                         id SERIAL PRIMARY KEY,
                         visitor_id INTEGER REFERENCES visitors(id) ON DELETE SET NULL,
                         ticket_type_id INTEGER REFERENCES ticket_types(id) ON DELETE CASCADE,
                         visit_schedule_id INTEGER REFERENCES visits_schedules(id) ON DELETE CASCADE,
                         time_slot_id INTEGER REFERENCES timeslots(id) ON DELETE CASCADE,
                         ticket_status ticket_status NOT NULL,
                         qr_code VARCHAR(255),
                         issued_at TIMESTAMP,
                         expires_at TIMESTAMP
);
