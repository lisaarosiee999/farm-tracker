#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SCHEMA_FILE="$ROOT_DIR/database/schema.sql"
DB_USER="${FARMTRACKER_DB_USER:-root}"
if [[ ${FARMTRACKER_DB_PASSWORD+x} ]]; then
  DB_PASSWORD="$FARMTRACKER_DB_PASSWORD"
else
  DB_PASSWORD="root"
fi

if ! command -v mysql >/dev/null 2>&1; then
  echo "mysql was not found. Install MySQL Server and make sure mysql is on PATH."
  exit 1
fi

echo "Creating database and tables from $SCHEMA_FILE ..."
if [[ -n "$DB_PASSWORD" ]]; then
  mysql -u "$DB_USER" "-p$DB_PASSWORD" < "$SCHEMA_FILE"
else
  mysql -u "$DB_USER" < "$SCHEMA_FILE"
fi
echo "Database setup complete."
