-- ENUMS
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TYPE residency AS ENUM ('LOCAL', 'INTERNATIONAL');
CREATE TYPE currency AS ENUM ('ETB', 'USD', 'EURO'); -- extend if needed
CREATE TYPE ticket_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED'); -- example statuses
CREATE TYPE announcement_type as ENUM (' PARTIAL_AVAILABILITY , TOTAL_CLOSURE');






-- Ticket Types Table
CREATE TABLE ticket_types (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              name VARCHAR(100) NOT NULL,
                              description TEXT,
                              image TEXT,
                              is_recommended BOOLEAN DEFAULT FALSE,
                              available BOOLEAN DEFAULT TRUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_ticket_policy
                                  FOREIGN KEY (ticket_policy_id)
                                      REFERENCES ticket_policy(id)
);


-- TIME SLOTS
CREATE TABLE timeslots (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           start_time TIME NOT NULL,
                            maxTickets INTEGER NOT NULL,
                           end_time TIME NOT NULL,
                           is_active BOOLEAN DEFAULT TRUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Visit Places Table

CREATE TABLE visit_places (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              name VARCHAR(100) NOT NULL,
                              available BOOLEAN DEFAULT TRUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


--visit schedule table
CREATE TABLE visits_schedules (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  date DATE NOT NULL,
                                  is_open BOOLEAN DEFAULT TRUE,
                                  reason_for_closing TEXT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Announcements Table
CREATE TABLE announcements (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               subject VARCHAR(255) NOT NULL,
                               announcement_type announcement_type NOT NULL,
                               message TEXT NOT NULL,
                               visit_schedule_id UUID,
                               effectiveDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (visit_schedule_id) REFERENCES visits_schedules(id) ON DELETE SET NULL
);






CREATE TABLE visit_schedule_place_status (
                                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                             visit_schedule_id UUID NOT NULL,
                                             visit_place_id UUID NOT NULL,
                                             is_open BOOLEAN DEFAULT TRUE,
                                             reason_for_closing TEXT,
                                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             FOREIGN KEY (visit_schedule_id) REFERENCES visits_schedules(id) ON DELETE CASCADE,
                                             FOREIGN KEY (visit_place_id) REFERENCES visit_places(id) ON DELETE CASCADE
);

CREATE TABLE tickets (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         ticket_type_id UUID REFERENCES ticket_types(id) ON DELETE CASCADE,
                         visit_schedule_id UUID REFERENCES visits_schedules(id) ON DELETE CASCADE,
                         time_slot_id UUID REFERENCES timeslots(id) ON DELETE CASCADE,
                         ticket_status ticket_status NOT NULL,
                         qr_code VARCHAR(255),
                         issued_at TIMESTAMP,
                         expires_at TIMESTAMP
);


-- PRICE CONFIGS
CREATE TABLE price_configs (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               ticket_type_id UUID REFERENCES ticket_types(id) ON DELETE CASCADE,
                               residency residency NOT NULL,
                               currency currency NOT NULL,
                               price DOUBLE PRECISION NOT NULL,
                               active BOOLEAN DEFAULT TRUE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UNIQUE (ticket_type_id, residency)
);


--Ticket Policy
CREATE TABLE ticket-policy (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             validityDays INTEGER NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

);

-- INVENTORIES
CREATE TABLE inventories (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             event_id BIGINT NOT NULL,
                             total INTEGER,
                             available INTEGER,
                             reserved INTEGER
);





-- QUEUE ENTRIES
CREATE TABLE queue_entries (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               visit_schedule_id UUID REFERENCES visits_schedules(id) ON DELETE CASCADE,
                               time_slot_id UUID REFERENCES timeslots(id) ON DELETE CASCADE,
                               queue_position INTEGER,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -- VISITORS (Assumed)
-- CREATE TABLE visitors (
--                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
--                           full_name VARCHAR(150),
--                           email VARCHAR(150),
--                           phone_number VARCHAR(50)
--                         visit_schedule_id INTEGER REFERENCES visits_schedules(id) ON DELETE CASCADE,
--     -- Add other visitor fields here
-- );

-- Join Table: Ticket Type <-> Visit Place
CREATE TABLE ticket_type_target_places (
                                           ticket_type_id UUID NOT NULL,
                                           visit_place_id UUID NOT NULL,
                                           PRIMARY KEY (ticket_type_id, visit_place_id),
                                           FOREIGN KEY (ticket_type_id) REFERENCES ticket_types(id) ON DELETE CASCADE,
                                           FOREIGN KEY (visit_place_id) REFERENCES visit_places(id) ON DELETE CASCADE
);

-- Join Table: Visit Schedule <-> Visit Place
CREATE TABLE visit_schedule_available_places (
                                                 visit_schedule_id UUID NOT NULL,
                                                 visit_place_id UUID NOT NULL,
                                                 PRIMARY KEY (visit_schedule_id, visit_place_id),
                                                 FOREIGN KEY (visit_schedule_id) REFERENCES visits_schedules(id) ON DELETE CASCADE,
                                                 FOREIGN KEY (visit_place_id) REFERENCES visit_places(id) ON DELETE CASCADE
);

-- Join Table: Announcement <-> Visit Place
CREATE TABLE announcement_target_places (
                                            announcement_id UUID NOT NULL,
                                            visit_place_id UUID NOT NULL,
                                            PRIMARY KEY (announcement_id, visit_place_id),
                                            FOREIGN KEY (announcement_id) REFERENCES announcements(id) ON DELETE CASCADE,
                                            FOREIGN KEY (visit_place_id) REFERENCES visit_places(id) ON DELETE CASCADE
);







