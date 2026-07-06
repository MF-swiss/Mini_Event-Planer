import { useState } from 'react'
import './App.css'
import Navigation from './components/Navigation'
import HomePage from './pages/HomePage'
import PageOne from './pages/PageOne'

function App() {
  const [currentPage, setCurrentPage] = useState('home')

  return (
    <div className="app-container">
      <Navigation currentPage={currentPage} onNavigate={setCurrentPage} />
      <main className="main-content">
        {currentPage === 'home' && (
          <HomePage onNavigate={setCurrentPage} />
        )}
        {currentPage === 'pageone' && (
          <PageOne onNavigate={setCurrentPage} />
        )}
      </main>
    </div>
  )
}

export default App;
