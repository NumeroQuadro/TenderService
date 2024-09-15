CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE employee
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username   VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TYPE organization_type AS ENUM (
    'IE',
    'LLC',
    'JSC'
    );
CREATE TYPE tender_status AS ENUM (
    'CREATED',
    'PUBLISHED',
    'CLOSED'
    );
CREATE TYPE bid_status AS ENUM (
    'CREATED',
    'PUBLISHED',
    'CANCELED'
    );

CREATE TABLE organization
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    type        organization_type DEFAULT 'LLC',
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE organization_responsible
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID REFERENCES organization (id) ON DELETE CASCADE,
    user_id         UUID REFERENCES employee (id) ON DELETE CASCADE
);

CREATE TABLE tender
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name            VARCHAR(100) NOT NULL,
    description     TEXT         NOT NULL,
    status          tender_status DEFAULT 'CREATED',
    organization_id UUID REFERENCES organization (id) ON DELETE CASCADE,
    creator_id      UUID REFERENCES employee (id) ON DELETE CASCADE,
    version         INT              DEFAULT 1,
    created_at      TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bid
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tender_id       UUID REFERENCES tender (id) ON DELETE CASCADE,
    organization_id UUID REFERENCES organization (id) ON DELETE CASCADE,
    creator_id      UUID REFERENCES employee (id) ON DELETE CASCADE,
    name            VARCHAR(100) NOT NULL,
    description     TEXT         NOT NULL,
    status          bid_status DEFAULT 'CREATED',
    version         INT              DEFAULT 1,
    created_at      TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bid_id      UUID REFERENCES bid (id) ON DELETE CASCADE,
    reviewer_id UUID REFERENCES employee (id) ON DELETE CASCADE,
    review_text TEXT NOT NULL,
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE approval
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bid_id      UUID NOT NULL REFERENCES bid (id) ON DELETE CASCADE,
    user_id     UUID NOT NULL REFERENCES employee (id) ON DELETE CASCADE,
    is_approved BOOLEAN NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
