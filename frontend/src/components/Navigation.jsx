export default function Navigation({ currentPage, onNavigate }) {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <h2 className="navbar-brand">NovaSphere Events</h2>
        <ul className="navbar-menu">
          <li>
            <button
              className={`navbar-link ${currentPage === 'home' ? 'active' : ''}`}
              onClick={() => onNavigate('home')}
            >
              Home
            </button>
          </li>
          <li>
            <button
              className={`navbar-link ${currentPage === 'pageone' ? 'active' : ''}`}
              onClick={() => onNavigate('pageone')}
            >
              Events Verwalten
            </button>
          </li>
        </ul>
      </div>
    </nav>
  );
}
