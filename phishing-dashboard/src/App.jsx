import { useState } from "react";
import axios from "axios";

function App() {

  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);

  const scanUrl = async () => {

    setLoading(true);

    try {

      const response = await axios.post(
        "http://localhost:8081/check-url",
        { url: url }
      );

      setResult(response.data);
      setHistory([response.data, ...history]);

    } catch (error) {

      console.error(error);
      alert("Backend connection failed");

    }

    setLoading(false);
  };

  const getStatusColor = (score) => {
    if (score >= 60) return "red";
    if (score >= 30) return "orange";
    return "green";
  };

  const getThreatLevel = (score) => {

  if (score >= 60) {
    return { text: "HIGH RISK 🔴", color: "#ef4444" };
  }

  if (score >= 30) {
    return { text: "MEDIUM RISK 🟠", color: "#f59e0b" };
  }

  return { text: "LOW RISK 🟢", color: "#22c55e" };

};

  return (

    <div className="cyber-bg" style={{ padding: "40px", fontFamily: "Arial", color: "white" }}>

      <h1 className="cyber-title">
        🔐 PHISHING DETECTION DASHBOARD
      </h1>

      <div style={{ textAlign: "center", marginBottom: "30px" }}>

        <input
          type="text"
          placeholder="Enter URL..."
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          style={{
            width: "400px",
            padding: "10px",
            borderRadius: "6px",
            marginRight: "10px"
          }}
        />

        <button
          onClick={scanUrl}
          style={{
            padding: "10px 20px",
            background: "#2563eb",
            color: "white",
            border: "none",
            borderRadius: "6px",
            cursor: "pointer",
            boxShadow: "0 0 10px #2563eb"
          }}
        >
          Scan
        </button>

      </div>

      {loading && (
        <p style={{ textAlign: "center", marginTop: "20px" }}>
          🔍 Scanning URL...
        </p>
      )}

      {result && (

        <div style={{
          background: "#1f2937",
          padding: "20px",
          borderRadius: "10px",
          maxWidth: "600px",
          margin: "auto",
          marginBottom: "40px"
        }}>

          <h2>Scan Result</h2>

          <p><b>URL:</b> {result.url}</p>

          <p><b>Risk Score:</b> {result.riskScore}</p>

          <div style={{
            width: "100%",
            background: "#374151",
            borderRadius: "6px",
            marginTop: "10px",
            height: "14px"
          }}>

            <div
              style={{
                width: `${result.riskScore}%`,
                height: "100%",
                borderRadius: "6px",
                transition: "width 0.6s ease",
                background:
                  result.riskScore >= 60
                    ? "#ef4444"
                    : result.riskScore >= 30
                    ? "#f59e0b"
                    : "#22c55e"
              }}
            />

          </div>

          <p style={{ color: getStatusColor(result.riskScore) }}>
            <b>Status:</b> {result.phishing ? "PHISHING ⚠️" : "SAFE ✅"}
          </p>

          <div
            style={{
              marginTop: "10px",
              padding: "6px 12px",
              display: "inline-block",
              borderRadius: "6px",
              background: getThreatLevel(result.riskScore).color,
              color: "white",
              fontWeight: "bold"
            }}
          >
            {getThreatLevel(result.riskScore).text}
          </div>

          {result.detectedIssues && (
            <>
              <h3>Detected Issues</h3>
              <ul>
                {result.detectedIssues.map((issue, index) => (
                  <li key={index}>{issue}</li>
                ))}
              </ul>
            </>
          )}

        </div>

      )}

      {result && (

        <div style={{
          background: "#111827",
          padding: "20px",
          borderRadius: "10px",
          maxWidth: "600px",
          margin: "auto",
          marginBottom: "40px"
        }}>

          <h2>🌐 Domain Intelligence</h2>

          <p>
            <b>Domain:</b>{" "}
            {new URL(result.url.startsWith("http") ? result.url : "https://" + result.url).hostname}
          </p>

          <p>
            <b>Top Level Domain:</b>{" "}
            {new URL(result.url.startsWith("http") ? result.url : "https://" + result.url)
              .hostname.split(".")
              .slice(-1)}
          </p>

          <p>
            <b>Subdomains:</b>{" "}
            {new URL(result.url.startsWith("http") ? result.url : "https://" + result.url)
              .hostname.split(".")
              .slice(0, -2)
              .join(".") || "None"}
          </p>

          <p>
            <b>Domain Length:</b>{" "}
            {new URL(result.url.startsWith("http") ? result.url : "https://" + result.url)
              .hostname.length}
          </p>

        </div>

      )}

      <div style={{ maxWidth: "800px", margin: "auto" }}>

        <h2>Scan History</h2>

        <table style={{ width: "100%", background: "#1f2937", borderRadius: "8px" }}>

          <thead>
            <tr>
              <th>URL</th>
              <th>Score</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>

            {history.map((item, index) => (

              <tr key={index}>

                <td>{item.url}</td>
                <td>{item.riskScore}</td>
                <td>{item.phishing ? "⚠️ Phishing" : "✅ Safe"}</td>

              </tr>

            ))}

          </tbody>

        </table>

      </div>

    </div>

  );
}

export default App;