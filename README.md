# JMagnet
JMagnet is a library written to make dealing in BitTorrent magnet URIs easier. It allows for parsing the entire URI into its components and getting useable information from it, in an easy to use API.

### ***This API is mainly designed around magnets for TV shows, so keep that in mind***

---

## Features:
- [x] Parsing magnets to their basic components (Topic, Name, Trackers etc)
- [x] Parse name to get more info (category, resolution, season, episode, seeds and peers)
- [x] Cache trackers from all magnets
- [x] API for custom magnet providers and trackers providers 
- [ ] A magnet downloader for downloading magnets URIs

---

## Currently implemented providers
### Magnet Providers:
- EZTV (https://eztv.re/)
- ThePirateBay (https://thepiratebay.org/)

### Tracker Providers:
- Ngosangs Trackers List (all) (https://github.com/ngosang/trackerslist)