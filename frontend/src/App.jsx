import "./App.css";
import Candidates from "./components/Candidates";
import Jobs from "./components/Jobs";

function App() {
  return (
    <div className="app">
      <header className="navbar">
        <h1>SkillBridge</h1>

        <nav>
          <a href="#">Dashboard</a>
          <a href="#">Candidates</a>
          <a href="#">Jobs</a>
          <a href="#">Matches</a>
        </nav>
      </header>

      <main className="main-content">
        {/* <Candidates /> */}
        <Jobs/>
      </main>
    </div>
  );
}

export default App;