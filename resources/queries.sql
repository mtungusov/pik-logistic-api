-- :name trackers-info :? :*
SELECT id, label, 
  status_movement, status_connection,
  event, event_time,
  group_id, group_title,
  zone_id, zone_parent_id, zone_label,
  zone_label_current, zone_label_prev, last_parent_inzone_time
FROM trackers_info
WHERE event_time > :time
ORDER BY (CASE
          WHEN event = 'inzone' OR (event = 'outzone' AND zone_parent_id IS NOT NULL) THEN 1
          WHEN event = 'outzone' AND zone_parent_id IS NULL THEN 2
          ELSE 3
          END ), zone_label_current, event_time DESC