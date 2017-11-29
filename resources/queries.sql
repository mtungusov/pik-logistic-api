-- :name trackers-info :? :*
SELECT id, label,
  status_movement, status_connection,
  event, event_time, status_last_update, status_gps_update,
  group_id, group_title,
  zone_id, zone_label,
  zone_parent_id, zone_parent_label,
  zone_label_current, zone_label_prev, last_parent_inzone_time
FROM trackers_info
WHERE event_time IS NOT NULL AND status_gps_update > :time
ORDER BY (CASE
          WHEN event = 'inzone' OR (event = 'outzone' AND zone_parent_id IS NOT NULL) THEN 1
          WHEN event = 'outzone' AND zone_parent_id IS NULL THEN 2
          ELSE 3
          END ), zone_label_current, event_time


-- :name trackers-info-by-etl :? :*
SELECT tracker_id, tracker_label, group_title, zone_label_in, time_in, zone_label_out, time_out,
  movement_status, connection_status, gps_updated
FROM trackers_info_by_etl
WHERE datediff(day, gps_updated, getdate()) < 10


-- :name groups :? :*
SELECT title FROM groups WHERE live = 1

-- :name zones :? :*
SELECT label FROM zones WHERE live = 1
