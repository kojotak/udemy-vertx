CREATE TABLE watchlist (
	account_id VARCHAR,
	asset VARCHAR,
	FOREIGN KEY (asset) REFERENCES assets (name),
	PRIMARY KEY (account_id, asset)
);