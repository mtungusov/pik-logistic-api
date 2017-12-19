-- :name trackers-info-by-etl :? :*
SELECT tracker_id, tracker_label, group_title, zone_label_in, time_in, zone_label_out, time_out,
  movement_status, connection_status, gps_updated
FROM trackers_info_by_etl
WHERE datediff(day, gps_updated, getdate()) < 10


-- :name groups :? :*
SELECT title FROM groups WHERE live = 1

-- :name zones :? :*
SELECT label FROM zones WHERE live = 1


--:name idle-by-geo :? :*
SELECT zone_label, count(duration) AS inzone_count, (avg(duration) / 60) AS avg_time_in_minutes
FROM statistic_inzone_duration
WHERE in_date BETWEEN :date_from AND :date_to
      AND zone_label IN (:v*:zones)
      AND group_title IN (:v*:groups)
      AND duration < (60 * 60 * 8)
GROUP BY zone_label


-- :name idle-by-group :? :*
SELECT group_title, count(duration) AS inzone_count, (avg(duration) / 60) AS avg_time_in_minutes
FROM statistic_inzone_duration
WHERE in_date BETWEEN :date_from AND :date_to
      AND zone_label IN (:v*:zones)
      AND group_title IN (:v*:groups)
      AND duration < (60 * 60 * 8)
GROUP BY group_title


-- :name idle-by-geo-and-group :? :*
SELECT zone_label, group_title, count(duration) AS inzone_count, (avg(duration) / 60) AS avg_time_in_minutes
FROM statistic_inzone_duration
WHERE in_date BETWEEN :date_from AND :date_to
      AND zone_label IN (:v*:zones)
      AND group_title IN (:v*:groups)
      AND duration < (60 * 60 * 8)
GROUP BY zone_label, group_title
