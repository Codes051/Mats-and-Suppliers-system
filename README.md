# University Cleaning Inventory & Issuance System — Track A

Java web application (JSP + Servlets + JDBC + PostgreSQL) for the
Programming 37(8)1 group project. See the Team Delivery Plan and SDLC
documents for architecture, roles, and phase breakdown — this README is
just "how do I get this running on my machine."

## What's already built (working scaffold)

- Maven project (`pom.xml`) with all required dependencies
- Database schema + seed data (`sql/schema.sql`, `sql/seed.sql`)
- `DBConnection` (HikariCP pool), `PasswordUtil` (BCrypt), `ValidationUtil`
- Full authentication: register, login, logout, `AuthFilter`, `RoleFilter`
- Full **Materials** module (Model → DAO → Service → Servlet → JSP) —
  this is the reference pattern to copy for every other module
- Dashboard (partially wired — Materials stats live, Cleaners/Issuance
  stats are TODO until those modules exist)
- Shared CSS, header/nav fragment, error page
- Stub servlets for Suppliers, Cleaners, Issuance, Reports with TODO
  comments explaining exactly what to build and the pattern to follow

## What each member still needs to build

| Owner | Module | Follow the pattern in |
|---|---|---|
| M3 | Suppliers | `MaterialServlet.java`, `MaterialDAO.java`, `MaterialService.java`, `Material.java` |
| M4 | Cleaners | same files as above |
| M4 | Stock Issuance (transactional) | see detailed TODO comment in `IssuanceServlet.java` |
| M5 | 4 Reports | see TODO comment in `ReportServlet.java` |
| M1 | Wire Cleaners/Issuance stats into Dashboard | see TODOs in `DashboardServlet.java` |

Each stub servlet (`SupplierServlet`, `CleanerServlet`, `IssuanceServlet`,
`ReportServlet`) has a comment block spelling out exactly which files to
create and what routes to expose.

## Prerequisites

- JDK 17+
- Maven 3.9+
- PostgreSQL 14+ running locally
- Apache Tomcat 10.1.x (Jakarta EE 10 namespace — **not** Tomcat 9)
- An IDE (IntelliJ IDEA Community or Eclipse for Enterprise Java both work)

## Setup — first time only

**1. Create the database**

```bash
createdb cleaninv
psql -d cleaninv -f sql/schema.sql
psql -d cleaninv -f sql/seed.sql
```

**2. Set your DB credentials**

Open `src/main/java/com/bc/cleaninv/util/DBConnection.java` and edit the
`URL`, `USER`, `PASSWORD` constants to match your local PostgreSQL setup.

**3. Build**

```bash
mvn clean package
```

This produces `target/cleaninv.war`.

**4. Deploy to Tomcat**

Copy `target/cleaninv.war` into your Tomcat `webapps/` folder, or if
your IDE has a Tomcat run configuration, point it at this project and
run it directly (usually faster for day-to-day development — you get
auto-redeploy on save).

**5. Open the app**

```
http://localhost:8080/cleaninv/
```

You'll land on the login page. Register a new account first (the seed
data's password hashes are placeholders — see the note in `seed.sql`),
then log in.

## Day-to-day workflow

1. `git pull` main before starting.
2. Create a branch: `git checkout -b feature/suppliers-crud` (use your
   module name).
3. Build your module following the Materials pattern.
4. Test it locally end-to-end.
5. Push and open a Pull Request. M1 reviews and merges.
6. Pull main again before your next session — interfaces may have
   changed (e.g. a new DAO method someone else added that you need).

## Project structure

```
src/main/java/com/bc/cleaninv/
  model/       POJOs (Material, User, ... add Supplier, Cleaner, Issuance)
  dao/         JDBC data access, one class per entity
  service/     Business rules & validation, sits between Servlet and DAO
  controller/  Servlets — one per module
  filter/      AuthFilter, RoleFilter
  util/        DBConnection, PasswordUtil, ValidationUtil
src/main/webapp/
  WEB-INF/views/   JSPs (not directly accessible by URL — must go through a servlet)
  css/             shared stylesheet
  index.jsp        redirects to /login or /dashboard
sql/
  schema.sql       run once to create tables
  seed.sql         run once to load demo data
```

## Common gotchas

- **Tomcat 9 vs 10**: this project uses `jakarta.servlet.*` imports
  (Tomcat 10.1+). If your Tomcat is version 9 or older, servlets will
  fail to load — upgrade Tomcat, don't downgrade the imports.
- **JSPs are under `WEB-INF/views/`**: this is intentional — it means
  users can't hit a JSP directly by URL (e.g.
  `/materials.jsp`), they must go through a servlet first, which is
  what enforces `AuthFilter`. Always forward to `/WEB-INF/views/...`
  from a servlet, never link to a JSP directly from HTML.
- **One connection per DAO call**: don't hold a `Connection` open
  across multiple methods — grab it, use it, close it (the
  try-with-resources pattern in `MaterialDAO` handles this
  automatically). The one exception is the Issuance transaction, where
  a single `Connection` is deliberately shared across two DAO calls —
  see the TODO in `IssuanceServlet.java`.
- **Passwords**: never store or log plain-text passwords. Always go
  through `PasswordUtil.hash()` / `PasswordUtil.verify()`.
